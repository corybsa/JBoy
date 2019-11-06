package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

import java.time.Instant;

public class GPU extends Observable<Double> {
    private final Memory memory;
    private final Display display;
    private Mode mode;
    private int scanline = 0;
    private int ticks = 0;
    private long previousCycles = 0;
    private int[][][] tiles = new int[384][8][8];
    private double lastFrame = Instant.now().getEpochSecond();

    private Observer<? super Double> observer;

    public enum Mode {
        HBLANK,
        VBLANK,
        OAM,
        VRAM
    }

    private interface Timings {
        int HBLANK = 51;
        int VBLANK = 114;
        int OAM = 20;
        int VRAM = 43;
    }

    GPU(Memory memory, Display display) {
        this.memory = memory;
        this.display = display;
        this.mode = Mode.HBLANK;
    }

    @Override
    public void subscribeActual(Observer<? super Double> observer) {
        this.observer = observer;
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
                    this.display.renderScanLine(this.tiles);
                    this.scanline++;

                    if(this.scanline == Display.VBlankArea.START) {
                        this.changeMode(Mode.VBLANK);
                    } else {
                        this.changeMode(Mode.OAM);
                    }

                    this.ticks -= Timings.HBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case VBLANK:
                if(this.ticks >= Timings.VBLANK) {
                    this.scanline++;

                    if(this.scanline > Display.VBlankArea.END) {
                        double now = Instant.now().getEpochSecond();
                        double delta = now - this.lastFrame;

                        if(delta > 0 && this.observer != null) {
                            this.observer.onNext((1 / delta) * 60.0d);
                        }

                        this.lastFrame = now;

                        this.scanline = 0;
                        this.changeMode(Mode.OAM);
                    }

                    this.ticks -= Timings.VBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case OAM:
                if(this.ticks >= Timings.OAM) {
                    this.changeMode(Mode.VRAM);
                    this.ticks -= Timings.OAM;
                }
                break;
            case VRAM:
                if(this.ticks >= Timings.VRAM) {
                    this.changeMode(Mode.HBLANK);
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
     * Requests an interrupt.
     * @param interrupt The interrupt to request.
     */
    private void requestInterrupt(int interrupt) {
        int interruptFlag = this.memory.getByteAt(IORegisters.INTERRUPT_FLAGS);
        interruptFlag |= interrupt;
        this.memory.setByteAt(IORegisters.INTERRUPT_FLAGS, interruptFlag);
    }

    private void changeMode(Mode mode) {
        if(mode != this.mode) {
            int statusFlag = this.memory.getByteAt(IORegisters.LCD_STATUS);

            switch(mode) {
                case HBLANK:
                    // set the mode interrupt bit 3 to 1 (bits 3 through 5)
                    statusFlag |= (1 << 3);

                    // set the mode bit to 0 (bits 0 and 1)
                    statusFlag = ((statusFlag >> 2) << 2);

                    this.memory.setByteAt(IORegisters.LCD_STATUS, statusFlag);
                    this.requestInterrupt(Interrupts.LCD_STAT);
                    break;
                case VBLANK:
                    // set the mode interrupt bit 4 to 1 (bits 3 through 5)
                    statusFlag |= (1 << 4);

                    // set the mode bit to 1 (bits 0 and 1)
                    statusFlag = ((statusFlag >> 2) << 2) | 1;

                    this.memory.setByteAt(IORegisters.LCD_STATUS, statusFlag);
                    this.requestInterrupt(Interrupts.VBLANK);
                    break;
                case OAM:
                    // set the mode interrupt bit 5 to 1 (bits 3 through 5)
                    statusFlag |= (1 << 5);

                    // set the mode bit to 2 (bits 0 and 1)
                    statusFlag = ((statusFlag >> 2) << 2) | 2;

                    this.memory.setByteAt(IORegisters.LCD_STATUS, statusFlag);
                    this.requestInterrupt(Interrupts.LCD_STAT);
                    break;
                case VRAM:
                    // set the mode bit to 2 (bits 0 and 1)
                    statusFlag = ((statusFlag >> 2) << 2) | 3;

                    this.memory.setByteAt(IORegisters.LCD_STATUS, statusFlag);
                    this.requestInterrupt(Interrupts.LCD_STAT);
                    break;
            }

            this.mode = mode;
        }
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
                pixelValue = Display.PixelColor.BLACK;
            } else if(lsb == 1 && msb == 0) {
                pixelValue = Display.PixelColor.DARK_GRAY;
            } else if(lsb == 0 && msb == 1) {
                pixelValue = Display.PixelColor.LIGHT_GRAY;
            } else {
                pixelValue = Display.PixelColor.WHITE;
            }

            this.tiles[tileIndex][rowIndex][pixelIndex] = pixelValue;
        }
    }
}
