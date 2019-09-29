package jboy.system;

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
public class Memory {
    private int[] cartridge = new int[0x7FFF];
    private int[] vram = new int[0x1FFF];
    private int[] sram = new int[0x1FFF];
    private int[] wram = new int[0x1FFF];
    private int[] eram = new int[0x1DFF];
    private int[] oam = new int[0x9F];
    private int[] fea0_feff = new int[0x5F];
    private int[] io = new int[0x48];
    private int[] ff4c_ff7f = new int[0x33];
    private int[] hram = new int[0x7E];
    private int[] ime = new int[1];

    public void loadROM(int[] rom) {
        this.cartridge = rom;
    }

    public int getByteAt(int address) {
        if(address <= 0x7FFF) {
            return this.cartridge[address];
        } else if(address <= 0x9FFF) {
            return this.vram[address];
        } else if(address <= 0xBFFF) {
            return this.sram[address];
        } else if(address <= 0xDFFF) {
            return this.wram[address];
        } else if(address <= 0xFDFF) {
            return this.eram[address];
        } else if(address <= 0xFE9F) {
            return this.oam[address];
        } else if(address <= 0xFEFF) {
            return this.fea0_feff[address];
        } else if(address <= 0xFF48) {
            return this.io[address];
        } else if(address <= 0xFF7F) {
            return this.ff4c_ff7f[address];
        } else if(address <= 0xFFFE) {
            return this.hram[address];
        } else {
            return this.ime[0];
        }
    }

    public void setByteAt(int address, int value) {
        // can't modify the ROM (0x0000 through 0x7FFF)

        if(address >= 0x8000 && address <= 0x9FFF) {
            this.vram[address] = value;
        } else if(address <= 0xBFFF) {
            this.sram[address] = value;
        } else if(address <= 0xDFFF) {
            this.wram[address] = value;
        } else if(address <= 0xFDFF) {
            this.eram[address] = value;
        } else if(address <= 0xFE9F) {
            this.oam[address] = value;
        } else if(address <= 0xFEFF) {
            this.fea0_feff[address] = value;
        } else if(address <= 0xFF48) {
            this.io[address] = value;
        } else if(address <= 0xFF7F) {
            this.ff4c_ff7f[address] = value;
        } else if(address <= 0xFFFE) {
            this.hram[address] = value;
        } else {
            this.ime[0] = value;
        }
    }
}
