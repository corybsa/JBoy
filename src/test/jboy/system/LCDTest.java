package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LCDTest {
    static private Memory memory;
    static private LCD lcd;
    static private GPU gpu;

    private static class Color {
        static final int WHITE = 0x9BBC0F;
        static final int LIGHT_GRAY = 0x8BAC0F;
        static final int DARK_GRAY = 0x306230;
        static final int BLACK = 0x0F380F;
    }

    private void createTile(int tileNum, int[] data) {
        int address = 0x8000 + (tileNum * 0x10);

        for(int i = 0; i < 0x10; i++) {
            memory.setByteAt(address + i, data[i]);
        }
    }

    private void loadTile(int row, int col, int tileNum) {
        memory.setByteAt(0x9800 + (row * 0x20) + col, tileNum);
    }

    private void drawScreenToText(byte[] tiles) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < tiles.length; i += 4) {
            if((i % (160 * 4)) == 0) {
                sb.append("\n");
            }

            int rgb = (((tiles[i + 2] + 256) & 0xFF) << 16) | (((tiles[i + 1] + 256) & 0xFF) << 8) | ((tiles[i] + 256) & 0xFF);

            switch(rgb) {
                case Color.WHITE:
                    sb.append("#");
                    break;
                case Color.LIGHT_GRAY:
                    sb.append("=");
                    break;
                case Color.DARK_GRAY:
                    sb.append("-");
                    break;
                case Color.BLACK:
                    sb.append(" ");
                    break;
            }
        }

        System.out.println(sb.toString());
    }

    private void checkPixel(byte[] tiles, int row, int col, int expectedColor) {
        int index = ((row * 160) + col) * 4;
        int rgb = (((tiles[index + 2] + 256) & 0xFF) << 16) | (((tiles[index + 1] + 256) & 0xFF) << 8) | ((tiles[index] + 256) & 0xFF);
        String message;

        switch(expectedColor) {
            case Color.WHITE:
                message = "should have a white pixel at " + row + ", " + col;
                break;
            case Color.LIGHT_GRAY:
                message = "should have a light gray pixel at " + row + ", " + col;
                break;
            case Color.DARK_GRAY:
                message = "should have a dark gray pixel at " + row + ", " + col;
                break;
            case Color.BLACK:
                message = "should have a black pixel at " + row + ", " + col;
                break;
            default:
                message = "undefined color (" + Integer.toHexString(rgb) + ") at " + row + ", " + col;
                break;
        }

        assertEquals(expectedColor, rgb, message);
    }

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        lcd = new LCD(memory);
        gpu = new GPU(memory, lcd);
        memory.setGpuRef(gpu);
    }

    @BeforeEach
    void setUp() {
        memory.setByteAt(IORegisters.WINDOW_X, 0);
        memory.setByteAt(IORegisters.WINDOW_Y, 0);
        memory.setByteAt(IORegisters.SCROLL_X, 0);
        memory.setByteAt(IORegisters.SCROLL_Y, 0);
        memory.setByteAt(IORegisters.LY_COORDINATE, 0);

        /*
            bits 7-6 are for color 3
            bits 5-4 are for color 2
            bits 3-2 are for color 1
            bits 1-0 are for color 0
        */
        memory.setByteAt(IORegisters.BG_PALETTE_DATA, 0b00_01_10_11);
    }

    @Test
    void renderTest() {
        lcd.setDrawFunction((tiles) -> {
            drawScreenToText(tiles);

            checkPixel(tiles, 0, 0, Color.WHITE);
            checkPixel(tiles, 0, 159, Color.LIGHT_GRAY);
            checkPixel(tiles, 143, 0, Color.DARK_GRAY);
            checkPixel(tiles, 143, 159, Color.WHITE);
            checkPixel(tiles, 0, 19, Color.BLACK);

            return null;
        });

        // enable lcd, use tile set at 0x9800 and enable background
        memory.setByteAt(IORegisters.LCDC, 0b10010001);

        // create white square tile at 0x01
        createTile(0x01, new int[] {
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111,
                0b11111111
        });

        // create light gray square tile at 0x02
        createTile(0x02, new int[] {
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111
        });

        // create dark gray square tile at 0x03
        createTile(0x03, new int[] {
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000,
                0b11111111,
                0b00000000
        });


        // load white square at the top left of the background map
        loadTile(0x00, 0x00, 0x01);

        // load light gray square at the top right of the background map
        loadTile(0x00, 0x13, 0x02);

        // load dark gray square at the bottom left of the background map
        loadTile(0x11, 0x00, 0x03);

        // load white square at the bottom right of the background map
        loadTile(0x11, 0x13, 0x01);

        lcd.render(gpu.getBackgroundMap());
    }
}
