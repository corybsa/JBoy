package jboy.system;

import jboy.other.GameBoyInfo;

public class GameBoy implements Runnable {
    private final CPU cpu;
    private final GPU gpu;
    private final Display display;
    private final Memory memory;
    private boolean isCartLoaded = false;
    private boolean isDebugging = false;

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
        if(!this.isDebugging) {
            this.cpu.reset();
            this.cpu.run();
        } else {
            this.cpu.reset();
        }
    }

    public String getCartridgeInfo() {
        this.isCartLoaded = true;
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

    public void setIsDebugging(boolean state) {
        this.isDebugging = state;
    }

    public void tick() {
        if(!this.isCartLoaded) {
            return;
        }

        this.cpu.tick();
    }

    public void runToBreakpoint() {
        if(!this.isCartLoaded) {
            return;
        }

        this.cpu.run();
    }

    public void addBreakpoint(int breakpoint) {
        this.cpu.addBreakpoint(breakpoint);
    }

    public void removeBreakpoint(int breakpoint) {
        this.cpu.removeBreakpoint(breakpoint);
    }

    public void resetCpu() {
        this.cpu.reset();
    }
}
