package jboy.system;

/**
 * Class for timer information
 *
 * For the TAC (Timer Control - 0xFF07) register, bit 2 determines if it is active or not and
 * bits 0 and 1 combine to determine the frequency:
 * 00:   4096 Hz
 * 01: 262144 Hz
 * 10:  65536 Hz
 * 11:  16384 Hz
 *
 * Since I'm using machine cycles the frequencies are:
 * 00:  4194304/4096 = 256 machine cycles
 * 01:  4194304/262144 = 4 machine cycles
 * 10:  4194304/65536 = 16 machine cycles
 * 11:  4194304/16384 = 64 machine cycles
 */
class Timers {
    static int divCounter = 0;
    static int timaCounter = 0;

    public interface TAC {
        int CLOCK1 = 256;
        int CLOCK2 = 4;
        int CLOCK3 = 16;
        int CLOCK4 = 64;
    }

    static int getFrequency(int frequency) {
        if(frequency == 0b00) {
            return TAC.CLOCK1;
        } else if(frequency == 0b01) {
            return TAC.CLOCK2;
        } else if(frequency == 0b10) {
            return TAC.CLOCK3;
        } else {
            return TAC.CLOCK4;
        }
    }
}
