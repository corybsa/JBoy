package jboy.other;

import jboy.system.GameBoy;

public class GameBoyInfo {
    private GameBoy gameBoy;
    private CpuInfo cpuInfo;
    private MemoryInfo memoryInfo;
    private TimerInfo timerInfo;

    public GameBoyInfo(GameBoy gb) {
        this.gameBoy = gb;
        this.cpuInfo = new CpuInfo(this.gameBoy.getCpu());
        this.memoryInfo = new MemoryInfo(this.gameBoy.getMemory());
        this.timerInfo = new TimerInfo(this.gameBoy.getMemory(), this.gameBoy.getTimers());
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
}
