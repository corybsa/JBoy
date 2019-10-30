package jboy.system;

class GPU {
    private final Memory memory;
    private final Display display;
    private Mode mode;
    private int scanline = 0;
    private int ticks = 0;
    private long previousCycles = 0;
    private int[] tileArray = new int[384 * 8 * 8];

    public enum Mode {
        HBLANK,
        VBLANK,
        OAM,
        VRAM
    }

    private interface Timings {
        int HBLANK = 51;
        int OAM = 20;
        int VRAM = 43;
        int VBLANK = 114;
    }

    GPU(Memory memory, Display display) {
        this.memory = memory;
        this.display = display;
        this.mode = Mode.HBLANK;
    }

    public void tick(long cycles) {
        this.ticks += cycles - this.previousCycles;

        // When cycles is set to 0 in the cpu, ticks becomes negative. This corrects that and sets ticks to the
        // proper value.
        if(this.ticks < 0) {
            this.ticks += this.previousCycles;
        }

        this.previousCycles = cycles;

        switch(this.mode) {
            case HBLANK:
                if(this.ticks >= Timings.HBLANK) {
                    this.display.renderScanLine(this.tileArray);
                    this.scanline++;

                    if(this.scanline == Display.VBlankArea.START) {
                        int interruptFlags = this.getInterruptFlag();
                        this.memory.setByteAt(IORegisters.INTERRUPT_FLAGS, interruptFlags | Interrupts.VBLANK);
                        this.mode = Mode.VBLANK;
                    } else {
                        this.mode = Mode.OAM;
                    }

                    this.ticks -= Timings.HBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case VBLANK:
                if(this.ticks >= Timings.VBLANK) {
                    this.scanline++;

                    if(this.scanline > Display.VBlankArea.END) {
                        this.scanline = 0;
                        this.mode = Mode.OAM;
                    }

                    this.ticks -= Timings.VBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case OAM:
                if(this.ticks >= Timings.OAM) {
                    this.mode = Mode.VRAM;
                    this.ticks -= Timings.OAM;
                }
                break;
            case VRAM:
                if(this.ticks >= Timings.VRAM) {
                    this.mode = Mode.HBLANK;
                    this.ticks -= Timings.VRAM;
                }
                break;
        }
    }

    /**
     * LCD Controller Y-Coordinate (LY) - Sets the value of LY.
     * The LY indicates the vertical line to which the present data is transferred to the LCD Driver.
     * @param value The value to set the LY register to.
     */
    private void setLY(int value) {
        this.memory.setByteAt(IORegisters.LCDC_Y_COORDINATE, value);
    }

    /**
     * Gets the value of the interrupt flag.
     * @return The value at memory address 0xFF0F
     */
    private int getInterruptFlag() {
        return this.memory.getByteAt(IORegisters.INTERRUPT_FLAGS);
    }

    Mode getMode() {
        return this.mode;
    }
}
