package jboy.other;

import jboy.system.IORegisters;
import jboy.system.Memory;

public class MemoryInfo {
    private Memory memory;

    MemoryInfo(Memory memory) {
        this.memory = memory;
    }

    public String getLCDC() {
        int lcdc = this.memory.getByteAt(IORegisters.LCDC);
        return "LCDC: " + String.format("0x%2s", Integer.toString(lcdc, 16).toUpperCase()).replace(" ", "0");
    }
}
