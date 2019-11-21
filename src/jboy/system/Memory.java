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
 *     - 0xFFFF         : IE (Interrupt Enable Register)
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
    private int[] io = new int[0x80];
    private int[] hram = new int[0x7F];
    private int[] ie = new int[1];

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

    int[] getROM() {
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
            addr += (this.currentRamBank * 0x2000);
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
            // Reading from this area on DMG always returns 0.
            return 0x00;

            /*addr = (0x5F - (0xFEFF - address)) & 0xFFFF;
            return this.fea0_feff[addr];*/
        } else if(address <= 0xFF7F) {
            addr = (0x4B - (0xFF4B - address)) & 0xFFFF;

            if(address == IORegisters.LCD_STATUS) {
                int lcdc = this.getByteAt(IORegisters.LCDC);

                // When LCD is off bits 0 through 2 return 0
                if((lcdc & 0x80) != 0x80) {
                    return (0x80 | this.io[addr]) & 0xF8;
                } else {
                    return (0x80 | this.io[addr]);
                }
            }

            // the upper 2 bits of the P1 always return 1
            if(address == IORegisters.JOYPAD) {
                return (0xC0 | this.io[addr]);
            }

            // bits 1 through 6 of SIO return 1
            if(address == IORegisters.SERIAL_TRANSFER_CONTROL) {
                return (0x7E | this.io[addr]);
            }

            // the upper 5 bits of TAC always return 1
            if(address == IORegisters.TAC) {
                return (0xF8 | this.io[addr]);
            }

            // the upper 3 bits of IF always return 1
            if(address == IORegisters.INTERRUPT_FLAGS) {
                return (0xE0 | this.io[addr]);
            }

            // the 7th bit of nr10 always returns 1
            if(address == IORegisters.SOUND1_SWEEP) {
                return (0x80 | this.io[addr]);
            }

            // bits 0 though 6 of nr30 always return 1
            if(address == IORegisters.SOUND3_ENABLE) {
                return (0x7F | this.io[addr]);
            }

            // bits 0 through 4 and bit 7 of nr32 always return 1
            if(address == IORegisters.SOUND3_OUTPUT_LEVEL) {
                return (0x9F | this.io[addr]);
            }

            // bits 6 and 7 of nr41 always return 1
            if(address == IORegisters.SOUND4_LENGTH) {
                return (0xC0 | this.io[addr]);
            }

            // bits 0 through 5 of nr44 always return 1
            if(address == IORegisters.SOUND4_INITIAL) {
                return (0x3F | this.io[addr]);
            }

            // bits 4 through 6 of nr52 always return 1
            if(address == IORegisters.SOUND_ENABLE) {
                return (0x70 | this.io[addr]);
            }

            return this.io[addr];
        } else if(address <= 0xFFFE) {
            addr = (0x7E - (0xFFFE - address)) & 0xFFFF;
            return this.hram[addr];
        } else {
            return this.ie[0];
        }
    }

    public void setByteAt(int address, int value) {
        int addr;

        // TODO: restrict access to VRAM and OAM when necessary

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

            // Writes to this area are copied to eram
            if(addr < 0x1E00) {
                this.eram[addr] = value;
            }
        } else if(address <= 0xFDFF) {
            addr = (0x1DFF - (0xFDFF - address)) & 0xFFFF;

            // Writes to this area are redirected to 0xC000 through 0xDDFF (wram)
            this.eram[addr] = value;
            this.wram[addr] = value;
        } else if(address <= 0xFE9F) {
            addr = (0x9F - (0xFE9F - address)) & 0xFFFF;
            this.oam[addr] = value;
        } else if(address <= 0xFEFF) {
            // writes are ignored on the GameBoy.
            /*addr = (0x5F - (0xFEFF - address)) & 0xFFFF;
            this.fea0_feff[addr] = value;*/
        } else if(address <= 0xFF7F) {
            addr = (0x4B - (0xFF4B - address)) & 0xFFFF;

            if(address == IORegisters.DIVIDER) {
                // TIMA can be increased if the system counter has reached half the clocks it needs to increase
                int tac = this.getByteAt(IORegisters.TAC);
                int tacFreq = Timers.getFrequency(tac & 0x03);
                int target = (CPU.FREQUENCY / tacFreq);

                if(Timers.systemCounter >= (target / 2)) {
                    this.incrementTima();
                }

                Timers.systemCounter = 0;
                this.io[addr] = 0;
                return;
            }

            if(address == IORegisters.TIMA) {
                if(Timers.state == Timers.TimerState.OVERFLOW) {
                    // If a value is written to TIMA during the overflow period, the new value will override the TMA load.
                    Timers.isTimaChanged = true;
                    Timers.timaGlitch = true;
                } else if(Timers.state == Timers.TimerState.LOADING_TMA) {
                    // If a value is written to TIMA during the period when TMA is being loaded, the write will be ignored.
                    Timers.isTimaChanged = true;
                }

                this.io[addr] = value;
            }

            if(address == IORegisters.TAC) {
                // When disabling the timer, if the system counter has reached half the clocks it
                // needs to increase, TIMA will increase
                int tac  = this.getByteAt(IORegisters.TAC);
                int oldEnable = (tac & 0x04);
                int newEnable = (value & 0x04);

                if((oldEnable == 0x04) && (newEnable == 0)) {
                    this.incrementTima();
                }

                // When changing TAC value, if the old selected bit was 0, the new one is 1 and the
                // new enable bit is 1, TIMA will increase
                int oldValue = (tac & 0x03);
                int newValue = (value & 0x03);

                if((oldValue == 0) && (newValue == 1) && (newEnable == 0x04)) {
                    this.incrementTima();
                }
            }

            if(address == IORegisters.INTERRUPT_FLAGS) {
                // TODO: if TIMA has a pending overflow, the written value will overwrite the automatic flag set to 1.
                // TODO: if a 0 is written during this time, the interrupt won't happen.
                if(Timers.state == Timers.TimerState.OVERFLOW) {
                    Timers.isFlagsChanged = true;
                } else if(Timers.state == Timers.TimerState.LOADING_TMA) {
                    Timers.isFlagsChanged = true;
                    Timers.flagValue = (value & Interrupts.TIMER) >> 2;
                }

                this.io[addr] = 0xE0 | value;
            } else {
                this.io[addr] = value;
            }

            // TODO: (in bgb) something weird is happening when a value is written to IORegisters.LCDC.
            // TODO: need to checkout IORegisters.LCD_STATUS as well.

            if(address == IORegisters.LCDC_Y_COORDINATE || address == IORegisters.LY_COMPARE) {
                this.compareLY();
            }
        } else if(address <= 0xFFFE) {
            addr = (0x7E - (0xFFFE - address)) & 0xFFFF;
            this.hram[addr] = value;
        } else {
            this.ie[0] = value;
        }
    }

    void updateDiv(int value) {
        int divAddr = (0x4B - (0xFF4B - IORegisters.DIVIDER)) & 0xFFFF;
        this.io[divAddr] = value;
    }

    private void incrementTima() {
        int tima = this.getByteAt(IORegisters.TIMA) + 1;

        if(tima > 0xFF) {
            tima = this.getByteAt(IORegisters.TMA);
            int flags = this.getByteAt(IORegisters.INTERRUPT_FLAGS);
            flags |= Interrupts.TIMER;
            this.setByteAt(IORegisters.INTERRUPT_FLAGS, flags);
        }

//        Timers.timaCounter = tima;
        this.setByteAt(IORegisters.TIMA, tima);
    }

    /**
     * The GameBoy permanently compares the value of the LYC and LY registers. When both values are identical,
     * the coincidence bit (6th bit) in the STAT register becomes set, and (if enabled) a STAT interrupt is requested.
     */
    private void compareLY() {
        int lyc = this.getByteAt(IORegisters.LY_COMPARE);
        int ly = this.getByteAt(IORegisters.LCDC_Y_COORDINATE);

        if(lyc == ly) {
            int status = this.getByteAt(IORegisters.LCD_STATUS);
            int interruptFlags = this.getByteAt(IORegisters.INTERRUPT_FLAGS);

            this.setByteAt(IORegisters.LCD_STATUS, status | (1 << 6));
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
