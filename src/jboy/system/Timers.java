package jboy.system;

/**
 * Class for timer information
 *
 * For the TAC (Timer Control - 0xFF07) register, bit 2 determines if it is active or not and
 * bits 0 and 1 combine to determine the frequency:
 * 00:  4194304/4096 = 1024 clock cycles
 * 01:  4194304/262144 = 16 clock cycles
 * 10:  4194304/65536 =  64 clock cycles
 * 11:  4194304/16384 = 256 clock cycles
 */
public class Timers {
    // The DIV counter is part of a 16 bit system counter, with DIV being the upper 8 bits. The default for the
    // system clock is 0xABCC.
    public static int systemCounter = 0xABCC;

    // This is to keep track of
    public static int timaCounter = 0;

    // Variables to keep track of TIMA overflow
    public static TimerState state = TimerState.COUNTING;
    static boolean isTimaChanged = false;
    static boolean isFlagsChanged = false;
    static int flagValue = 1;
    static boolean timaGlitch = false;
    private int overflowCycles = 0;

    // The frequencies in Hz.
    public interface TAC {
        int CLOCK0 = 4096;
        int CLOCK1 = 262144;
        int CLOCK2 = 65536;
        int CLOCK3 = 16384;
    }

    public enum TimerState {
        COUNTING,
        OVERFLOW,
        LOADING_TMA
    }

    private Memory memory;

    Timers(Memory memory) {
        this.memory = memory;
    }

    public static int getFrequency(int frequency) {
        if(frequency == 0b00) {
            return TAC.CLOCK0;
        } else if(frequency == 0b01) {
            return TAC.CLOCK1;
        } else if(frequency == 0b10) {
            return TAC.CLOCK2;
        } else {
            return TAC.CLOCK3;
        }
    }

    void reset() {
        Timers.timaCounter = 0;
        Timers.systemCounter = 0xABCC;
        Timers.state = TimerState.COUNTING;
        Timers.isTimaChanged = false;
        Timers.isFlagsChanged = false;
        Timers.flagValue = 1;
        Timers.timaGlitch = false;
        this.overflowCycles = 0;
    }

    void tick(int cycles) {
        this.incrementSystemCounter(cycles);

        switch(Timers.state) {
            case COUNTING:
                // Get the timer frequency.
                int tac = this.memory.getByteAt(IORegisters.TAC);
                boolean isEnabled = (tac & 0x04) == 0x04;

                if(isEnabled) {
                    Timers.timaCounter += cycles;
                    int tacFreq = Timers.getFrequency(tac & 0x03);

                    // Check if TIMA has passed the max amount of clocks.
                    if(Timers.timaCounter >= (CPU.FREQUENCY / tacFreq)) {
                        int tima = this.memory.getByteAt(IORegisters.TIMA) + 1;

                        if(tima > 0xFF) {
                            // When TIMA overflows, it's value is 0 for 4 cycles and the interrupt is also delayed during this time.
                            tima = 0x00;
                            this.memory.setByteAt(IORegisters.TIMA, tima);

                            // Keep track of the delay
                            Timers.state = TimerState.OVERFLOW;
                            this.overflowCycles = Timers.timaCounter - (CPU.FREQUENCY / tacFreq);
                        } else {
                            this.memory.setByteAt(IORegisters.TIMA, tima);
                        }
                    }
                }
                break;
            case OVERFLOW:
                // Check if TIMA overflowed last cycle.
                this.overflowCycles += cycles;

                // The TMA load takes 4 clock cycles.
                if(this.overflowCycles < 4) {
                    return;
                }

                // If a value is written to TIMA during the overflow period, the new value will override the TMA load.
                if(!Timers.isTimaChanged) {
                    // Set TIMA to the value of TMA
                    int tma = this.memory.getByteAt(IORegisters.TMA);
                    this.memory.setByteAt(IORegisters.TIMA, tma);
                }

                Timers.state = TimerState.LOADING_TMA;
                Timers.isTimaChanged = false;
                Timers.isFlagsChanged = false;
                break;
            case LOADING_TMA:
                // If a value is written to TIMA during the period when TMA is being loaded, the write will be ignored.
                // There's another scenario to take into account. When TMA is written during this period, TIMA will also
                //   be loaded with that value. However, since the timer ticks after the CPU does TMA should already have
                //   the new value and TIMA will receive it without us doing anything.
                if(Timers.isTimaChanged && !Timers.timaGlitch) {
                    // Set TIMA to the value of TMA
                    int tma = this.memory.getByteAt(IORegisters.TMA);
                    this.memory.setByteAt(IORegisters.TIMA, tma);
                }

                // If IF is written during this period, the written value will overwrite the automatic flag set to 1.
                // If a 0 is written during this cycle, the interrupt won't happen.
                // The TIMA glitch prevents this from happening regardless of other conditions.
                if(Timers.isFlagsChanged && Timers.flagValue == 1 && !Timers.timaGlitch) {
                    // Request interrupt.
                    int flags = this.memory.getByteAt(IORegisters.INTERRUPT_FLAGS);
                    flags |= Interrupts.TIMER;
                    this.memory.setByteAt(IORegisters.INTERRUPT_FLAGS, flags);
                }

                Timers.isTimaChanged = false;
                Timers.isFlagsChanged = false;
                Timers.timaGlitch = false;
                Timers.state = TimerState.COUNTING;
                break;
        }
    }

    private void incrementSystemCounter(int cycles) {
        Timers.systemCounter += cycles;

        if(Timers.systemCounter > 0xFFFF) {
            Timers.systemCounter = 0;
        }

        this.memory.updateDiv(Timers.systemCounter >> 8);
    }
}
