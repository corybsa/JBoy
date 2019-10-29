package jboy.system;

import java.util.ArrayList;

class GPU {
    private final Memory memory;
    private final Display display;
    private Mode mode;
    private int scanline = 0;
    private int ticks = 0;
    private long previousCycles = 0;
    private int[][][] tiles = new int[384][8][8];
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
     * Gets the value of the interrupt enable.
     * @return The value at memory address 0xFFFF
     */
    private int getInterruptEnable() {
        return this.memory.getByteAt(IORegisters.INTERRUPT_ENABLE);
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

    void updateTiles(int address) {
        int vramAddress = (0x1FFF - (0x9FFF - address)) & 0xFFFF;

        if(vramAddress >= 0x1800) {
            return;
        }

        int index = address & 0xFFFE;
        int byte1 = this.memory.getByteAt(index);
        int byte2 = this.memory.getByteAt(index + 1);

        int tileIndex = vramAddress / 16;
        int rowIndex = (vramAddress % 16) / 2;
        int pixelIndex;
        int pixelValue;

        for(pixelIndex = 0; pixelIndex < 8; pixelIndex++) {
            int mask = 1 << (7 - pixelIndex);
            int lsb = (byte1 & mask) >> (7 - pixelIndex);
            int msb = (byte2 & mask) >> (7 - pixelIndex);

            if(lsb == 1 && msb == 1) {
                pixelValue = Display.PixelColor.WHITE;
            } else if(lsb == 1 && msb == 0) {
                pixelValue = Display.PixelColor.DARK_GRAY;
            } else if(lsb == 0 && msb == 1) {
                pixelValue = Display.PixelColor.LIGHT_GRAY;
            } else {
                pixelValue = Display.PixelColor.BLACK;
            }

//            this.tiles[tileIndex][rowIndex][pixelIndex] = pixelValue;

            this.tileArray[tileIndex * rowIndex * pixelIndex] = pixelValue;
        }
    }
}
