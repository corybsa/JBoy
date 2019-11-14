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

    private byte[] tiles = new byte[HEIGHT * WIDTH * 4];
    private Memory memory;

    private Observer<? super byte[]> observer;

    public interface VBlankArea {
        int START = 144;
        int END = 153;
    }

    public interface PixelColor {
        byte WHITE = 0;
        byte LIGHT_GRAY = 1;
        byte DARK_GRAY = 2;
        byte BLACK = 3;
    }

    Display(Memory memory) {
        this.memory = memory;
    }

    @Override
    protected void subscribeActual(Observer<? super byte[]> observer) {
        this.observer = observer;
    }

    void render(byte[][][] backgroundMap) {
        int lcdc = this.memory.getByteAt(IORegisters.LCDC);

        // LCD is disabled, skip render.
        if((lcdc >> 7) == 0) {
            return;
        }

        boolean isBGEnabled = (lcdc & 0x01) == 0x01;
        boolean isSpritesEnabled = ((lcdc >> 1) & 0x01) == 0x01;

        if(isBGEnabled) {
            this.renderViewport(backgroundMap);
        }

        if(isSpritesEnabled) {
            this.renderSprites(lcdc);
        }

        observer.onNext(this.tiles);
    }

    private void renderViewport(byte[][][] backgroundMap) {
        int scrollX = this.memory.getByteAt(IORegisters.SCROLL_X);
        int scrollY = this.memory.getByteAt(IORegisters.SCROLL_Y);

        int index;

        for(int row = 0; row < HEIGHT; row++) {
            int y = row + scrollY;

            if(y >= GPU.BG_HEIGHT) {
                y -= GPU.BG_HEIGHT;
            }

            for(int col = 0; col < WIDTH; col++) {
                int x = col + scrollX;

                if(x >= GPU.BG_WIDTH) {
                    x -= GPU.BG_WIDTH;
                }

                byte color = backgroundMap[y][x][x % 8];
                byte[] pixels = this.getColor(color);

                index = ((row * WIDTH) + col) * 4;

                this.tiles[index] = pixels[0];
                this.tiles[index + 1] = pixels[1];
                this.tiles[index + 2] = pixels[2];
                this.tiles[index + 3] = pixels[3];
            }
        }
    }

    private void renderSprites(int lcdc) {

    }

    private byte[] getColor(byte color) {
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

        return new byte[] { blue, green, red, (byte)0xFF };
    }
}
