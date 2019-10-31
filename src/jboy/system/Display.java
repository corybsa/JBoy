package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Display extends Observable<byte[]> {
    // The refresh rate of the display in frames per second. 59.7 / 4 = 14.925
    static final float FREQUENCY = 59.7f;

    // The amount of machine cycles per frame.
    static final int LCDC_PERIOD = (int)(CPU.FREQUENCY / Display.FREQUENCY);

    public static final int HEIGHT = 144;
    public static final int WIDTH = 160;

    private byte[] tileArray = new byte[HEIGHT * WIDTH * 4];
    private byte[][] tiles = new byte[HEIGHT][WIDTH * 4];
    private Memory memory;
    private Observer<? super byte[]> observer;

    public interface VBlankArea {
        int START = 143;
        int END = 153;
    }

    public interface PixelColor {
        int WHITE = 0;
        int DARK_GRAY = 1;
        int LIGHT_GRAY = 2;
        int BLACK = 3;
    }

    Display(Memory memory) {
        this.memory = memory;
    }

    @Override
    protected void subscribeActual(Observer<? super byte[]> observer) {
        this.observer = observer;
    }

    void renderScanLine(int[][][] tiles) {
        int lcdc = this.memory.getByteAt(IORegisters.LCDC);

        // LCD is disabled, skip render.
        if((lcdc >> 7) == 0) {
            return;
        }

        boolean isBGEnabled = (lcdc & 0x01) == 0x01;
        boolean isSpritesEnabled = ((lcdc >> 1) & 0x01) == 0x01;

        if(isBGEnabled) {
            this.renderBG(lcdc, tiles);
        }

        if(isSpritesEnabled) {
            this.renderSprites(lcdc);
        }

        observer.onNext(this.tileArray);
//        observer.onNext(this.tiles);
    }

    private void renderBG(int lcdc, int[][][] tiles) {
        boolean isWindowEnabled = false;
        boolean unsigned = true;
        int scanlineY = this.memory.getByteAt(IORegisters.LCDC_Y_COORDINATE);
        int windowOffset = (lcdc >> 6) & 0x01;
        int bgWindowOffset = (lcdc >> 4) & 0x01;
        int bgOffset = (lcdc >> 3) & 0x01;
        int bgAddress;
        int tileData;
        int scrollX = this.memory.getByteAt(IORegisters.SCROLL_X);
        int scrollY = this.memory.getByteAt(IORegisters.SCROLL_Y);
        int windowX = this.memory.getByteAt(IORegisters.WINDOW_X) - 7;
        int windowY = this.memory.getByteAt(IORegisters.WINDOW_Y);

        // Is the window enabled?
        if(((lcdc >> 5) & 0x01) == 0x01) {
            // Is the current scanline within the bounds of the window y coordinate?
            if(windowY <= scanlineY) {
                isWindowEnabled = true;
            }
        }

        // Check which tile set to use.
        if(bgWindowOffset == 1) {
            tileData = 0x8000;
        } else {
            tileData = 0x8800;
            unsigned = false;
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
        }

        int y;

        if(!isWindowEnabled) {
            y = scrollY + scanlineY;
        } else {
            y = scanlineY - windowY;
        }

        int index = 0;

        for(int row = 0; row < Display.HEIGHT; row++) {
            for(int col = 0; col < Display.WIDTH; col++) {
                int tileRow = (row / 8) * 32;
                int tileCol = col / 8;
                int tileNum;

                int tileAddress = bgAddress + tileRow + tileCol;

                if(unsigned) {
                    tileNum = this.memory.getByteAt(tileAddress);
                } else {
                    tileNum = (byte)this.memory.getByteAt(tileAddress);
                }

                int color = tiles[tileNum][row % 8][col % 8];
                byte red = (byte)0xFF;
                byte green = (byte)0xFF;
                byte blue = (byte)0xFF;

                switch(color) {
                    case PixelColor.WHITE:
                        red = (byte)0xFF;
                        green = (byte)0xFF;
                        blue = (byte)0xFF;
                        break;
                    case PixelColor.LIGHT_GRAY:
                        red = (byte)0xCC;
                        green = (byte)0xCC;
                        blue = (byte)0xCC;
                        break;
                    case PixelColor.DARK_GRAY:
                        red = 0x77;
                        green = 0x77;
                        blue = 0x77;
                        break;
                    case PixelColor.BLACK:
                        red = 0x00;
                        green = 0x00;
                        blue = 0x00;
                        break;
                }

                if(scanlineY < 0 || scanlineY > 143) {
                    continue;
                }

                this.tileArray[index] = blue;
                this.tileArray[index + 1] = green;
                this.tileArray[index + 2] = red;
                this.tileArray[index + 3] = (byte)0xFF;
                index += 4;
            }
        }
    }

    private void renderSprites(int lcdc) {

    }

    private int getColor(int num, int address) {
        int result = PixelColor.WHITE;
        int palette = this.memory.getByteAt(address);
        int high = 0;
        int low = 0;

        switch(num) {
            case 0x00:
                high = 1;
                low = 0;
                break;
            case 0x01:
                high = 3;
                low = 2;
                break;
            case 0x02:
                high = 5;
                low = 4;
                break;
            case 0x03:
                high = 7;
                low = 6;
                break;
        }

        int color = ((palette & (1 << high)) >> low) | ((palette & (1 << low)) >> low);

        switch(color) {
            case 0x00:
                result = PixelColor.WHITE;
                break;
            case 0x01:
                result = PixelColor.LIGHT_GRAY;
                break;
            case 0x02:
                result = PixelColor.DARK_GRAY;
                break;
            case 0x03:
                result = PixelColor.BLACK;
                break;
        }

        return result;
    }
}
