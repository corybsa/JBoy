package jboy.system;

import jboy.other.CpuInfo;
import jboy.other.GameBoyInfo;

public class GameBoy {
    private CPU cpu;
    private Memory memory;

    public GameBoy() {
        this.memory = new Memory();
        this.cpu = new CPU(this.memory);
    }

    public void loadROM(int[] rom) {
        this.memory.loadROM(rom);
        this.getCartridgeInfo();

        this.cpu.run();
    }

    public int[] getSprites() {
        int[] sprites = new int[0xA0];

        for(int i = 0; i < 0x9F; i++) {
            sprites[i] = this.memory.getByteAt(0xFF00 + i);
        }

        return sprites;
    }

    public int[] getNintendo() {
        int[] nintendo = new int[0x30];

        for(int i = 0; i < 0x2F; i++) {
            nintendo[i] = this.memory.getByteAt(i);
        }

        return nintendo;
    }

    public String getCartridgeInfo() {
        return new Cartridge(this.memory.getROM()).toString();
    }

    public GameBoyInfo getInfo() {
        return new GameBoyInfo(this);
    }

    public CPU getCpu() {
        return this.cpu;
    }

    public Memory getMemory() {
        return this.memory;
    }
}
