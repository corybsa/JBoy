package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

import java.time.Instant;

public class GPU extends Observable<Double> {
    public static final int BG_HEIGHT = 256;
    public static final int BG_WIDTH = 256;

    private final Memory memory;
    private final Display display;
    private Mode mode;
    private int scanline = 0;
    private int ticks = 0;
    private long previousCycles = 0;
    private byte[][][] tiles = new byte[384][8][8];
    private double lastFrame = Instant.now().getEpochSecond();

    private byte[][][] backgroundMap = new byte[BG_HEIGHT][BG_WIDTH][8];
    private byte[] backgroundTiles = new byte[BG_HEIGHT * BG_WIDTH * 4];

    private Observer<? super Double> observer;

    public enum Mode {
        HBLANK,
        VBLANK,
        OAM,
        VRAM
    }

    private interface Timings {
        int HBLANK = 204;
        int VBLANK = 456;
        int OAM = 80;
        int VRAM = 172;
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
                    this.display.render(this.backgroundMap);
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
                            this.observer.onNext((1.0 / delta) * 60.0d);
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
            this.createBackgroundMap();
            return;
        }

        int index = address & 0xFFFE;
        int byte1 = this.memory.getByteAt(index);
        int byte2 = this.memory.getByteAt(index + 1);
        int tileIndex = vramAddress / 16;
        int rowIndex = (vramAddress % 16) / 2;
        int pixelIndex;
        byte pixelValue;

        for(pixelIndex = 0; pixelIndex < 8; pixelIndex++) {
            int mask = 1 << (7 - pixelIndex);
            int msb = (byte1 & mask) >> (7 - pixelIndex);
            int lsb = (byte2 & mask) >> (7 - pixelIndex);

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

    private void createBackgroundMap() {
        boolean isWindowEnabled = false;
        int scanlineY = this.memory.getByteAt(IORegisters.LCDC_Y_COORDINATE);
        int lcdc = this.memory.getByteAt(IORegisters.LCDC);
        int windowOffset = (lcdc >> 6) & 0x01;
        int tileSet = (lcdc >> 4) & 0x01;
        int bgOffset = (lcdc >> 3) & 0x01;
        int windowY = this.memory.getByteAt(IORegisters.WINDOW_Y);
        int index;
        int bgAddress = 0x9800;
        int tileOffset;
        boolean isUnsigned;

        // Is the window enabled?
        /*if(((lcdc >> 5) & 0x01) == 0x01) {
            // Is the current scanline within the bounds of the window y coordinate?
            if(windowY <= scanlineY) {
                isWindowEnabled = true;
            }
        }

        if(!isWindowEnabled) {
            // Check which tile set to use.
            if(bgOffset == 1) {
                bgAddress = 0x9C00;
            } else {
                bgAddress = 0x9800;
            }
        } else {
            if(windowOffset == 1) {
                bgAddress = 0x9C00;
            } else {
                bgAddress = 0x9800;
            }
        }*/

        for(int row = 0; row < BG_HEIGHT; row++) {
            int tileRow = (row / 8) * 32;

            for(int col = 0; col < BG_WIDTH; col++) {
                int tileCol = col / 8;
                int tileNum;

                int tileAddress = bgAddress + tileRow + tileCol;
                tileNum = this.memory.getByteAt(tileAddress);

                if(tileSet == 0 && tileNum < 0x80) {
                    tileNum += 0x100;
                }

                byte color = this.tiles[tileNum][row % 8][col % 8];
                this.backgroundMap[row][col][col % 8] = color;
            }
        }
    }
}
