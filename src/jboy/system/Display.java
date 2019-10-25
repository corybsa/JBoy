package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Display extends Observable<byte[]> {
    // The refresh rate of the display in frames per second.
    public static final float FREQUENCY = 59.7f;
    public static final int LCDC_PERIOD = 17556;

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
        byte[] data = new byte[300];

        int i = 0;

        for (int y = 0; y < 10; y++) {
            int r = y * 255 / 10;

            for (int x = 0; x < 10; x++) {
                int g = x * 255 / 10;
                data[i] = (byte)r;
                data[i + 1] = (byte)g;
                data[i + 2] = (byte)255;
                i += 3;
            }
        }

        for(int j = 0; j < data.length; j += 3) {
            data[j] = (byte)Math.floor(Math.random() * 255);
            data[j + 1] = (byte)Math.floor(Math.random() * 255);
            data[j + 2] = (byte)Math.floor(Math.random() * 255);
        }

        return data;
    }
}
