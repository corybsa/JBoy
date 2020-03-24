package jboy.other;

import jboy.system.GameBoy;

import java.util.function.Function;

public class GameBoyInfo {
    private CpuInfo cpuInfo;
    private MemoryInfo memoryInfo;
    private TimerInfo timerInfo;
    private Function<GameBoyInfo, Void> debugUpdateFunction;

    public GameBoyInfo(GameBoy gb) {
        this.cpuInfo = new CpuInfo(gb.getCpu());
        this.memoryInfo = new MemoryInfo(gb.getMemory());
        this.timerInfo = new TimerInfo(gb.getMemory(), gb.getTimers());
    }

    public CpuInfo getCpuInfo() {
        return this.cpuInfo;
    }

    public MemoryInfo getMemoryInfo() {
        return this.memoryInfo;
    }

    public TimerInfo getTimerInfo() {
        return this.timerInfo;
    }

    public void setDebugUpdateFunction(Function<GameBoyInfo, Void> function) {
        this.debugUpdateFunction = function;
    }

    public void updateDebugInfo() {
        this.debugUpdateFunction.apply(this);
    }
}
