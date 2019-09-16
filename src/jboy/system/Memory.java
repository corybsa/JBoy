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
    private byte[] cartridge = new byte[0x7FFF];
    private byte[] vram = new byte[0x1FFF];
    private byte[] sram = new byte[0x1FFF];
    private byte[] wram = new byte[0x1FFF];
    private byte[] eram = new byte[0x1DFF];
    private byte[] oam = new byte[0x9F];
    private byte[] fea0_feff = new byte[0x5F];
    private byte[] io = new byte[0x48];
    private byte[] ff4c_ff7f = new byte[0x33];
    private byte[] hram = new byte[0x7E];
    private byte[] ime = new byte[1];

    public void loadROM(byte[] rom) {
        this.cartridge = rom;
    }

    public byte getByteAt(int n) {
        if(n <= 0x7FFF) {
            return this.cartridge[n];
        } else if(n <= 0x9FFF) {
            return this.vram[n];
        } else if(n <= 0xBFFF) {
            return this.sram[n];
        } else if(n <= 0xDFFF) {
            return this.wram[n];
        } else if(n <= 0xFDFF) {
            return this.eram[n];
        } else if(n <= 0xFE9F) {
            return this.oam[n];
        } else if(n <= 0xFEFF) {
            return this.fea0_feff[n];
        } else if(n <= 0xFF48) {
            return this.io[n];
        } else if(n <= 0xFF7F) {
            return this.ff4c_ff7f[n];
        } else if(n <= 0xFFFE) {
            return this.hram[n];
        } else {
            return this.ime[0];
        }
    }
}
