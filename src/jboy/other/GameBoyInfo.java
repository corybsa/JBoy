package jboy.other;

import jboy.system.GameBoy;

public class GameBoyInfo {
    private GameBoy gameBoy;

    public GameBoyInfo(GameBoy gb) {
        this.gameBoy = gb;
    }

    public CpuInfo getCpuInfo() {
        return new CpuInfo(this.gameBoy.getCpu());
    }

    public MemoryInfo getMemoryInfo() {
        return new MemoryInfo(this.gameBoy.getMemory());
    }
}
