package jboy.system;

import jboy.other.GameBoyInfo;

public class GameBoy implements Runnable {
    private final CPU cpu;
    private final GPU gpu;
    private final Display display;
    private final Memory memory;

    public GameBoy() {
        this.memory = new Memory();
        this.display = new Display(this.memory);
        this.gpu = new GPU(this.memory, this.display);
        this.cpu = new CPU(this.memory, this.gpu);

        this.memory.subscribe(this.gpu::updateTiles);
    }

    public void loadROM(int[] rom) {
        this.memory.loadROM(rom);
        this.getCartridgeInfo();
    }

    @Override
    public void run() {
        this.cpu.run();
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

    public Display getDisplay() {
        return this.display;
    }
}
