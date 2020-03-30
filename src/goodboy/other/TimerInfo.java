package goodboy.other;

import goodboy.system.CPU;
import goodboy.system.IORegisters;
import goodboy.system.Memory;
import goodboy.system.Timers;

public class TimerInfo {
    private Memory memory;
    private Timers timers;

    TimerInfo(Memory memory, Timers timers) {
        this.memory = memory;
        this.timers = timers;
    }

    public String getTimer() {
        return "Timer: " + String.format("0x%4s", Integer.toHexString(Timers.systemCounter).toUpperCase()).replace(" ", "0");
    }

    public String getTimerState() {
        switch(Timers.state) {
            case COUNTING:
                return "Timer State: Counting";
            case OVERFLOW:
                return "Timer State: Overflow";
            case LOADING_TMA:
                return "Timer State: TMA Load";
            default:
                return "Timer State: Unknown";
        }
    }

    public String getTIMA() {
        int tima = this.memory.getByteAt(IORegisters.TIMA);
        return "TIMA: " + String.format("0x%2s", Integer.toHexString(tima).toUpperCase()).replace(" ", "0");
    }

    public String getTMA() {
        int tma = this.memory.getByteAt(IORegisters.TMA);
        return "TMA: " + String.format("0x%2s", Integer.toHexString(tma).toUpperCase()).replace(" ", "0");
    }

    public String getTacState() {
        int tac = this.memory.getByteAt(IORegisters.TAC);
        return "TAC: " + ((tac & 0x04) == 0x04 ? "Enabled" : "Disabled");
    }

    public String getFrequency() {
        int tac = this.memory.getByteAt(IORegisters.TAC);
        int freq = Timers.getFrequency(tac & 0x03);
        int clocks = CPU.FREQUENCY / freq;

        return String.format("Freq: %dHz (%d clocks)", freq, clocks);
    }

    public String getTimaClocks() {
        return String.format("TIMA Clocks: %d", Timers.timaCounter);
    }
}
