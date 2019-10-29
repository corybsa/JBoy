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
    private int[] sram = new int[0x2000];
    private int[] wram = new int[0x2000];
    private int[] eram = new int[0x1E00];
    private int[] oam = new int[0xA0];
    private int[] fea0_feff = new int[0x60];
    private int[] io = new int[0x4C];
    private int[] ff4c_ff7f = new int[0x34];
    private int[] hram = new int[0x7F];
    private int[] ime = new int[1];
    private Observer<? super Integer> observer;

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        this.observer = observer;
    }

    public void loadROM(int[] rom) {
        this.cartridge = rom;
    }

    public int[] getROM() {
        return this.cartridge;
    }

    public int getByteAt(int address) {
        int addr;

        if(address <= 0x7FFF) {
            return this.cartridge[address];
        } else if(address <= 0x9FFF) {
            addr = (0x1FFF - (0x9FFF - address)) & 0xFFFF;
            return this.vram[addr];
        } else if(address <= 0xBFFF) {
            addr = (0x1FFF - (0xBFFF - address)) & 0xFFFF;
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

        // can't modify the ROM (0x0000 through 0x7FFF)
        if(address <= 0x7FFF) {
            // nothing
        } else if(address <= 0x9FFF) {
            addr = (0x1FFF - (0x9FFF - address)) & 0xFFFF;
            this.vram[addr] = value;
//            this.observer.onNext(address);
        } else if(address <= 0xBFFF) {
            addr = (0x1FFF - (0xBFFF - address)) & 0xFFFF;
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
}
