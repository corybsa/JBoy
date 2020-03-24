package jboy.system;

import jboy.disassembler.Disassembler;
import jboy.other.GameBoyInfo;

import java.util.ArrayList;

public class GameBoy implements Runnable {
    private final Timers timers;
    private final CPU cpu;
    private final GPU gpu;
    private final Display display;
    private final Memory memory;
    private final GameBoyInfo info;
    private int[] rom;
    private Disassembler disassembler;
    private boolean isCartLoaded = false;
    private boolean isDebugging = false;

    public GameBoy() {
        this.memory = new Memory();
        this.timers = new Timers(this.memory);
        this.display = new Display(this.memory);
        this.gpu = new GPU(this.memory, this.display);
        this.cpu = new CPU(this.memory, this.gpu, this.timers);

        this.memory.setGpuRef(this.gpu);

        this.info = new GameBoyInfo(this);
    }

    public void loadROM(int[] rom) {
        this.cpu.reset();
        this.memory.loadROM(rom);
        this.rom = rom;
        this.isCartLoaded = true;

        if(this.isDebugging) {
            byte[] cart = new byte[rom.length];

            for(int i = 0; i < rom.length; i++) {
                cart[i] = (byte)rom[i];
            }

            this.disassembler = new Disassembler(cart);
            this.disassembler.disassemble();
        }
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

    public int getCartridgeSize() {
        return this.rom.length;
    }

    public int[] getRom() {
        return this.rom;
    }

    public GameBoyInfo getInfo() {
        return this.info;
    }

    public CPU getCpu() {
        return this.cpu;
    }

    public Memory getMemory() {
        return this.memory;
    }

    public Timers getTimers() {
        return this.timers;
    }

    public Display getDisplay() {
        return this.display;
    }

    public GPU getGpu() {
        return this.gpu;
    }

    public ArrayList<String> getDisassembly() {
        if(this.disassembler != null) {
            return this.disassembler.getDisassemblyList();
        }

        return null;
    }

    public void setIsDebugging(boolean state) {
        this.isDebugging = state;
    }

    public void tick() {
        if(!this.isCartLoaded) {
            return;
        }

        if(this.isDebugging) {
            this.info.updateDebugInfo();
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
