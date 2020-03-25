package jboy.system;

import java.util.function.Function;

public class LCD {
    // The refresh rate of the display in frames per second. 59.7 / 4 = 14.925
    static final float FREQUENCY = 59.7f;

    // The amount of machine cycles per frame.
    static final int LCDC_PERIOD = (int)(CPU.FREQUENCY / LCD.FREQUENCY);

    public static final int HEIGHT = 144;
    public static final int WIDTH = 160;

    private byte[] tiles = new byte[HEIGHT * WIDTH * 4];
    private Memory memory;
    private Function<byte[], Void> draw;

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

    public LCD(Memory memory) {
        this.memory = memory;
    }

    public void render(byte[][][] backgroundMap) {
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

        this.draw.apply(this.tiles);
    }

    public void setDrawFunction(Function<byte[], Void> func) {
        this.draw = func;
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
        /*

        FF47 - BGP - BG Palette Data
        Bit 7-6 - Shade for Color BLACK
        Bit 5-4 - Shade for Color DARK GRAY
        Bit 3-2 - Shade for Color LIGHT GRAY
        Bit 1-0 - Shade for Color WHITE

        */
        byte red = (byte)0xFF;
        byte green = (byte)0xFF;
        byte blue = (byte)0xFF;

        int palette = this.memory.getByteAt(IORegisters.BG_PALETTE_DATA);
        int high = 0;
        int low = 0;

        switch(color) {
            case PixelColor.WHITE:
               high = 1;
               low = 0;
               break;
            case PixelColor.LIGHT_GRAY:
                high = 3;
                low = 2;
                break;
            case PixelColor.DARK_GRAY:
                high = 5;
                low = 4;
                break;
            case PixelColor.BLACK:
                high = 7;
                low = 6;
                break;
        }

        int paletteColor = ((palette & (0x01 << high)) >> low) | ((palette & (0x01 << low)) >> low);

        switch(paletteColor) {
            case PixelColor.WHITE:
                red = (byte)0x9B;
                green = (byte)0xBC;
                blue = (byte)0x0F;
                break;
            case PixelColor.LIGHT_GRAY:
                red = (byte)0x8B;
                green = (byte)0xAC;
                blue = (byte)0x0F;
                break;
            case PixelColor.DARK_GRAY:
                red = 0x30;
                green = 0x62;
                blue = 0x30;
                break;
            case PixelColor.BLACK:
                red = 0x0F;
                green = 0x38;
                blue = 0x0F;
                break;
        }

        return new byte[] { blue, green, red, (byte)0xFF };
    }
}
