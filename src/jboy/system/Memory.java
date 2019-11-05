package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * The GameBoy has 64KB of Memory.
 *
 * Notes:
 *   - Address 0xE000 - 0xFE00 appear to access the internal RAM the same as 0xC000 - 0xDE00.
 *     i.e. if you write to 0xE000 it will appear at 0xC000 AND 0xE000. Similarly, writing to 0xC000 will appear at 0xC000 AND 0xE000.
 *
 *   - Memory regions:
 *     - 0x0000 - 0x3FFF: ROM (16KB ROM bank #0 (in cartridge))
 *     - 0x4000 - 0x7FFF: ROM (16KB switchable ROM bank (in cartridge))
 *                        - I think these can safely be combined for a range of 0x0000 - 0x7FFF
 *
 *     - 0x8000 - 0x9FFF: VRAM (8KB Video RAM)
 *                        - Can only be accessed during LCD modes 0 (HBlank), 1 (VBlank) and 2 (OAM Search)
 *
 *     - 0xA000 - 0xBFFF: SRAM (8KB switchable RAM bank)
 *                        - Most of the time this is used for save data as it's backed up by a battery.
 *                        - Can be used as extra work RAM.
 *                        - By default, SRAM is in a locked state, which ignores writes and returns somewhat random values when read.
 *                        - How SRAM is unlocked depends on the MBC.
 *
 *     - 0xC000 - 0xDFFF: WRAM (8KB internal RAM)
 *                        - This is physically in the GameBoy itself and can be used however the programmer wants.
 *
 *     - 0xE000 - 0xFDFF: ERAM (Echo of 8KB Internal RAM)
 *                        - Basically points to 0xC000 - 0xDE00
 *                        - It's recommended to avoid relying on this though.
 *
 *     - 0xFE00 - 0xFE9F: OAM (Object Attribute Memory or Sprite Attribute Memory)
 *                        - The area of memory where information about objects is stored.
 *                        - Locked while the PPU is accessing it.
 *
 *     - 0xFEA0 - 0xFEFF: Empty but unusable for I/O
 *
 *     - 0xFF00 - 0xFF4B: IO (I/O Ports)
 *                        - A bunch of hardware registers.
 *                        - Here is where you can configure graphics, play sound or communicate with another GameBoy.
 *
 *     - 0xFF4C - 0xFF7F: Empty but unusable for I/O
 *
 *     - 0xFF80 - 0xFFFE: HRAM (Internal RAM or High RAM)
 *                        - These bytes work just like WRAM, except that they can be accessed slightly faster by a certain instruction.
 *                        - This is a great place to store temporary variables because of the speed.
 *
 *     - 0xFFFF         : IME (Interrupt Master Enable or Interrupt Enable Register)
 *                        - Special byte of I/O.
 *                        - It's here because of how the CPU works internally.
 */
public class Memory extends Observable<Integer> {
    private int[] cartridge = new int[0x800000];
    private int[] vram = new int[0x2000];
    private int[] sram = new int[0x8000];
    private int[] wram = new int[0x2000];
    private int[] eram = new int[0x1E00];
    private int[] oam = new int[0xA0];
    private int[] fea0_feff = new int[0x60];
    private int[] io = new int[0x4C];
    private int[] ff4c_ff7f = new int[0x34];
    private int[] hram = new int[0x7F];
    private int[] ime = new int[1];

    private RomBank romBankType;
    private int currentRomBank = 1;
    private int currentRamBank = 0;
    private boolean isRomEnabled = true;
    private boolean isRamEnabled = false;
    private Observer<? super Integer> observer;

    enum RomBank {
        NONE,
        MBC1,
        MBC2
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        this.observer = observer;
    }

    public void loadROM(int[] rom) {
        this.cartridge = rom;
        this.romBankType = this.getRomBankType(this.cartridge[0x147]);
    }

    public int[] getROM() {
        return this.cartridge;
    }

    public int getByteAt(int address) {
        int addr;

        if(address <= 0x3FFF) {
            return this.cartridge[address];
        } else if(address <= 0x7FFF) {
            addr = (address - 0x4000) + (this.currentRomBank * 0x4000);
            return this.cartridge[addr];
        } else if(address <= 0x9FFF) {
            addr = (0x1FFF - (0x9FFF - address)) & 0xFFFF;
            return this.vram[addr];
        } else if(address <= 0xBFFF) {
            addr = (0x1FFF - (0xBFFF - address)) & 0xFFFF;
            addr = (addr - 0x1FFF) + (this.currentRamBank * 0x2000);
            return this.sram[addr];
        } else if(address <= 0xDFFF) {
            addr = (0x1FFF - (0xDFFF - address)) & 0xFFFF;
            return this.wram[addr];
        } else if(address <= 0xFDFF) {
            addr = (0x1DFF - (0xFDFF - address)) & 0xFFFF;
            return this.eram[addr];
        } else if(address <= 0xFE9F) {
            addr = (0x9F - (0xFE9F - address)) & 0xFFFF;
            return this.oam[addr];
        } else if(address <= 0xFEFF) {
            addr = (0x5F - (0xFEFF - address)) & 0xFFFF;
            return this.fea0_feff[addr];
        } else if(address <= 0xFF4B) {
            addr = (0x4B - (0xFF4B - address)) & 0xFFFF;
            return this.io[addr];
        } else if(address <= 0xFF7F) {
            addr = (0x33 - (0xFF7F - address)) & 0xFFFF;
            return this.ff4c_ff7f[addr];
        } else if(address <= 0xFFFE) {
            addr = (0x7E - (0xFFFE - address)) & 0xFFFF;
            return this.hram[addr];
        } else {
            return this.ime[0];
        }
    }

    public void setByteAt(int address, int value) {
        int addr;

        if(address <= 0x7FFF) {
            this.switchBank(address, value);
        } else if(address <= 0x9FFF) {
            addr = (0x1FFF - (0x9FFF - address)) & 0xFFFF;
            this.vram[addr] = value;
            this.observer.onNext(address);
        } else if(address <= 0xBFFF) {
            addr = (0x1FFF - (0xBFFF - address)) & 0xFFFF;

            if(this.isRamEnabled) {
                addr = (addr - 0x1FFF) + (this.currentRamBank * 0x2000);
            }

            this.sram[addr] = value;
        } else if(address <= 0xDFFF) {
            addr = (0x1FFF - (0xDFFF - address)) & 0xFFFF;
            this.wram[addr] = value;
        } else if(address <= 0xFDFF) {
            addr = (0x1DFF - (0xFDFF - address)) & 0xFFFF;
            this.eram[addr] = value;
        } else if(address <= 0xFE9F) {
            addr = (0x9F - (0xFE9F - address)) & 0xFFFF;
            this.oam[addr] = value;
        } else if(address <= 0xFEFF) {
            addr = (0x5F - (0xFEFF - address)) & 0xFFFF;
            this.fea0_feff[addr] = value;
        } else if(address <= 0xFF4B) {
            addr = (0x4B - (0xFF4B - address)) & 0xFFFF;

            if(address == IORegisters.DIVIDER) {
                this.io[addr] = 0;
                return;
            }

            // TODO: I think when you write to IORegisters.INTERRUPT_FLAGS, only the lower nibble is written.

            // TODO: (in bgb) something weird is happening when a value is written to IORegisters.LCDC.

            this.io[addr] = value;

            if(address == IORegisters.LCDC_Y_COORDINATE || address == IORegisters.LY_COMPARE) {
                this.compareLY();
            }
        } else if(address <= 0xFF7F) {
            addr = (0x33 - (0xFF7F - address)) & 0xFFFF;
            this.ff4c_ff7f[addr] = value;
        } else if(address <= 0xFFFE) {
            addr = (0x7E - (0xFFFE - address)) & 0xFFFF;
            this.hram[addr] = value;
        } else {
            this.ime[0] = value;
        }
    }

    /**
     * The GameBoy permanently compares the value of the LYC and LY registers. When both values are identical,
     * the coincidence bit (6th bit) in the STAT register becomes set, and (if enabled) a STAT interrupt is requested.
     */
    private void compareLY() {
        int lyc = this.getByteAt(IORegisters.LY_COMPARE);
        int ly = this.getByteAt(IORegisters.LCDC_Y_COORDINATE);

        if(lyc == ly) {
            int interruptFlags = this.getByteAt(IORegisters.INTERRUPT_FLAGS);

            this.setByteAt(IORegisters.LCD_STATUS, (1 << 6));
            this.setByteAt(IORegisters.INTERRUPT_FLAGS, interruptFlags | Interrupts.LCD_STAT);
        }
    }

    private RomBank getRomBankType(int value) {
        switch(value) {
            default:
            case 0x00:
                return RomBank.NONE;
            case 0x01:
            case 0x02:
            case 0x03:
                return RomBank.MBC1;
            case 0x05:
            case 0x06:
                return RomBank.MBC2;
        }
    }

    private void switchBank(int address, int value) {
        if(address <= 0x1FFF) {
            if(this.romBankType == RomBank.MBC1 || this.romBankType == RomBank.MBC2) {
                this.enableRamBanking(address, value);
            }
        } else if(address <= 0x3FFF) {
            if(this.romBankType == RomBank.MBC1 || this.romBankType == RomBank.MBC2) {
                this.changeLowRomBank(value);
            }
        } else if(address <= 0x5FFF) {
            if(this.romBankType == RomBank.MBC1) {
                if(this.isRomEnabled) {
                    this.changeHighRomBank(value);
                } else {
                    this.changeRamBank(value);
                }
            }
        } else if(address <= 0x7FFF) {
            if(this.romBankType == RomBank.MBC1) {
                this.changeBankMode(value);
            }
        }
    }

    private void enableRamBanking(int address, int value) {
        // When a game wants to enable RAM banking bit 4 must be 0 for MBC2 cartridges
        if(this.romBankType == RomBank.MBC2 && (address >> 4) == 1) {
            return;
        }

        // When a game wants to write to RAM banks, the lower nibble must be 0x0A.
        this.isRamEnabled = (value & 0x0F) == 0x0A;
    }

    private void changeLowRomBank(int value) {
        if(this.romBankType == RomBank.MBC2) {
            this.currentRomBank = value & 0x0F;

            // rom bank can't be zero
            if(this.currentRomBank == 0) {
                this.currentRomBank = 1;
            }

            return;
        }

        this.currentRomBank = (this.currentRomBank & 0xE0) | (value & 0x1F);

        if(this.currentRomBank == 0) {
            this.currentRomBank = 1;
        }
    }

    private void changeHighRomBank(int value) {
        this.currentRomBank = (this.currentRomBank & 0x1F) | (value & 0xE0);

        if(this.currentRomBank == 0) {
            this.currentRomBank = 1;
        }
    }

    private void changeRamBank(int value) {
        this.currentRamBank = value & 0x03;
    }

    private void changeBankMode(int value) {
        if((value & 0x01) == 0) {
            this.isRomEnabled = true;
            this.currentRamBank = 0;
        } else {
            this.isRomEnabled = false;
        }
    }
}
