package jboy.other;

import jboy.system.CPU;

import java.util.ArrayList;

public class CpuInfo {
    private CPU cpu;

    public CpuInfo(CPU cpu) {
        this.cpu = cpu;
    }

    public CPU getCpu() {
        return cpu;
    }

    public CpuInfo update(CPU cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getPC() {
        return "PC: " + String.format("0x%4s", Integer.toHexString(this.cpu.getPC()).toUpperCase()).replace(" ", "0");
    }

    public String getSP() {
        return "SP: " + String.format("0x%4s", Integer.toHexString(this.cpu.getSP()).toUpperCase()).replace(" ", "0");
    }

    public String getAF() {
        return "AF: " + String.format("0x%4s", Integer.toHexString(this.cpu.getAF()).toUpperCase()).replace(" ", "0");
    }

    public String getBC() {
        return "BC: " + String.format("0x%4s", Integer.toHexString(this.cpu.getBC()).toUpperCase()).replace(" ", "0");
    }

    public String getDE() {
        return "DE: " + String.format("0x%4s", Integer.toHexString(this.cpu.getDE()).toUpperCase()).replace(" ", "0");
    }

    public String getHL() {
        return "HL: " + String.format("0x%4s", Integer.toHexString(this.cpu.getHL()).toUpperCase()).replace(" ", "0");
    }

    public String getInterruptFlags() {
        return "IF: " + String.format("0x%2s", Integer.toHexString(this.cpu.getInterruptFlag()).toUpperCase()).replace(" ", "0");
    }

    public String getInterruptEnable() {
        return "IE: " + String.format("0x%2s", Integer.toHexString(this.cpu.getInterruptEnable()).toUpperCase()).replace(" ", "0");
    }

    public String getIME() {
        return "IME: " + (this.cpu.getIME() ? "on" : "off");
    }

    public ArrayList<String> getBreakpoints() {
        ArrayList<String> result = new ArrayList<>();

        for(Integer breakpoint : this.cpu.getBreakpoints()) {
            result.add(String.format("0x%4s", Integer.toHexString(breakpoint).toUpperCase()).replace(" ", "0"));
        }

        return result;
    }
}
