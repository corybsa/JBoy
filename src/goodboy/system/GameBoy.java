package goodboy.system;

import goodboy.disassembler.Disassembler;
import goodboy.other.GameBoyInfo;

import java.util.HashMap;

public class GameBoy implements Runnable {
    private final Timers timers;
    private final CPU cpu;
    private final GPU gpu;
    private final LCD lcd;
    private final Memory memory;
    private final GameBoyInfo info;
    private int[] rom;
    private Disassembler disassembler;
    private boolean isCartLoaded = false;
    private boolean isDebugging = false;

    public GameBoy() {
        this.memory = new Memory();
        this.timers = new Timers(this.memory);
        this.lcd = new LCD(this.memory);
        this.gpu = new GPU(this.memory, this.lcd);
        this.cpu = new CPU(this.memory, this.gpu, this.timers);

        this.memory.setGpuRef(this.gpu);

        this.info = new GameBoyInfo(this);
    }

    public void loadROM(int[] rom) {
        this.reset();
        this.memory.loadROM(rom);
        this.rom = rom;
        this.isCartLoaded = true;

        if(this.isDebugging) {
            int[] cart = new int[rom.length];

            for(int i = 0; i < rom.length; i++) {
                cart[i] = rom[i];
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
            this.reset();
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

    public LCD getLCD() {
        return this.lcd;
    }

    public GPU getGpu() {
        return this.gpu;
    }

    public Disassembler getDisassembler() {
        return this.disassembler;
    }

    public HashMap<Integer, String> getDisassembly() {
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

        this.cpu.tick();

        if(this.isDebugging) {
            this.info.updateDebugInfo();
        }
    }

    public void runToBreakpoint() {
        if(!this.isCartLoaded) {
            return;
        }

        this.cpu.run();
        this.info.updateDebugInfo();
    }

    public void addBreakpoint(int breakpoint) {
        this.cpu.addBreakpoint(breakpoint);
        this.info.updateDebugInfo();
    }

    public void removeBreakpoint(int breakpoint) {
        this.cpu.removeBreakpoint(breakpoint);
        this.info.updateDebugInfo();
    }

    public void reset() {
        this.rom = null;
        this.cpu.reset();
        this.gpu.reset();

        if(this.isDebugging) {
            this.info.updateDebugInfo();
        }
    }
}
