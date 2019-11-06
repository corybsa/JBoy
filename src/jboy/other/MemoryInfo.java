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
        return "LCDC: " + String.format("0x%2s", Integer.toHexString(lcdc).toUpperCase()).replace(" ", "0");
    }

    public String getLY() {
        int ly = this.memory.getByteAt(IORegisters.LCDC_Y_COORDINATE);
        return "LY: " + String.format("0x%2s", Integer.toHexString(ly).toUpperCase()).replace(" ", "0");
    }

    public String getLCDStatus() {
        int stat = this.memory.getByteAt(IORegisters.LCD_STATUS);
        return "STAT: " + String.format("0x%2s", Integer.toHexString(stat).toUpperCase()).replace(" ", "0");
    }
}
