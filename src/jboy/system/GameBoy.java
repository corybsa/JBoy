package jboy.system;

public class GameBoy {
    private CPU cpu;
    private Memory memory;

    public GameBoy() {
        this.memory = new Memory();
        this.cpu = new CPU(this.memory);
    }

    public void loadROM(byte[] rom) {
        this.memory.loadROM(rom);
    }
}
