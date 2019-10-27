package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Display extends Observable<byte[]> {
    // The refresh rate of the display in frames per second.
    static final float FREQUENCY = 59.7f;

    // The amount of machine cycles per frame.
    static final int LCDC_PERIOD = (int)(CPU.FREQUENCY / Display.FREQUENCY);

    public static final int HEIGHT = 144;
    public static final int WIDTH = 160;

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

    @Override
    protected void subscribeActual(Observer<? super byte[]> observer) {
        this.observer = observer;
    }

    void renderScanLine(int[][][] tiles) {


        observer.onNext(this.createImage());
    }

    private byte[] createImage() {
        // multiply by 3 because each pixel is represented by 3 bytes.
        // total length of array is 69120
        byte[] pixels = new byte[Display.HEIGHT * Display.WIDTH * 3];

        for(int i = 0; i < pixels.length; i += 3) {
            pixels[i] = (byte)Math.floor(Math.random() * 255);
            pixels[i + 1] = (byte)Math.floor(Math.random() * 255);
            pixels[i + 2] = (byte)Math.floor(Math.random() * 255);
        }

        return pixels;
    }
}
