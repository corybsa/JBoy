package jboy.system;

import java.time.Instant;

public class GPU  {
    public static final int BG_HEIGHT = 256;
    public static final int BG_WIDTH = 256;

    private final Memory memory;
    private final LCD lcd;
    private int mode;
    private int scanline = 0;
    private int ticks = 0;
    private long previousCycles = 0;
    private byte[][][] tiles = new byte[384][8][8];
    private double lastFrame = Instant.now().getEpochSecond();

    private byte[][][] backgroundMap = new byte[BG_HEIGHT][BG_WIDTH][8];

    public interface Mode {
        int HBLANK = 0;
        int VBLANK = 1;
        int OAM = 2;
        int VRAM = 3;
    }

    private interface Timings {
        int HBLANK = 204;
        int VBLANK = 456;
        int OAM = 80;
        int VRAM = 172;
    }

    public GPU(Memory memory, LCD LCD) {
        this.memory = memory;
        this.lcd = LCD;
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
            case Mode.HBLANK:
                if(this.ticks >= Timings.HBLANK) {
                    this.lcd.render(this.backgroundMap);
                    this.scanline++;

                    if(this.scanline == LCD.VBlankArea.START) {
                        this.changeMode(Mode.VBLANK);
                    } else {
                        this.changeMode(Mode.OAM);
                    }

                    this.ticks -= Timings.HBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case Mode.VBLANK:
                if(this.ticks >= Timings.VBLANK) {
                    this.scanline++;

                    if(this.scanline > LCD.VBlankArea.END) {
                        this.scanline = 0;
                        this.changeMode(Mode.OAM);
                    }

                    this.ticks -= Timings.VBLANK;
                    this.setLY(this.scanline);
                }
                break;
            case Mode.OAM:
                if(this.ticks >= Timings.OAM) {
                    this.changeMode(Mode.VRAM);
                    this.ticks -= Timings.OAM;
                }
                break;
            case Mode.VRAM:
                if(this.ticks >= Timings.VRAM) {
                    this.changeMode(Mode.HBLANK);
                    this.ticks -= Timings.VRAM;
                }
                break;
        }
    }

    void reset() {
        this.mode = Mode.HBLANK;
        this.scanline = 0;
        this.ticks = 0;
        this.previousCycles = 0;
        this.tiles = new byte[384][8][8];
        this.backgroundMap = new byte[BG_HEIGHT][BG_WIDTH][8];

        this.setLY(this.scanline);
//        this.display.render(this.backgroundMap);
    }

    /**
     * LCD Controller Y-Coordinate (LY) - Sets the value of LY.
     * The LY indicates the vertical line to which the present data is transferred to the LCD Driver.
     * @param value The value to set the LY register to.
     */
    private void setLY(int value) {
        this.memory.setByteAt(IORegisters.LY_COORDINATE, value);
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

    private void changeMode(int mode) {
        if(mode != this.mode) {
            int statusFlag = this.memory.getByteAt(IORegisters.LCD_STATUS);

            // set the mode bits in LCD_STATUS
            statusFlag = (statusFlag & ~(mode)) | mode;

            // set the mode interrupt bit (bits 3 through 5)
            statusFlag |= (1 << (mode + 3));

            switch(mode) {
                case Mode.VBLANK:
                    this.requestInterrupt(Interrupts.VBLANK);

                    break;
                case Mode.HBLANK:
                case Mode.OAM:
                case Mode.VRAM:
                    this.requestInterrupt(Interrupts.LCD_STAT);

                    break;
            }

            this.memory.setByteAt(IORegisters.LCD_STATUS, statusFlag);
            this.mode = mode;
        }
    }

    int getMode() {
        return this.mode;
    }

    public void updateTiles(int address) {
        int vramAddress = (0x1FFF - (0x9FFF - address)) & 0xFFFF;

        if(vramAddress >= 0x1800) {
            this.updateBackgroundMap();
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
                pixelValue = LCD.PixelColor.BLACK;
            } else if(lsb == 1 && msb == 0) {
                pixelValue = LCD.PixelColor.DARK_GRAY;
            } else if(lsb == 0 && msb == 1) {
                pixelValue = LCD.PixelColor.LIGHT_GRAY;
            } else {
                pixelValue = LCD.PixelColor.WHITE;
            }

            this.tiles[tileIndex][rowIndex][pixelIndex] = pixelValue;
        }
    }

    private void updateBackgroundMap() {
        boolean isWindowEnabled = false;
        int scanlineY = this.memory.getByteAt(IORegisters.LY_COORDINATE);
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

    public byte[][][] getBackgroundMap() {
        return this.backgroundMap;
    }
}
