package jboy.system;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Display extends Observable<Integer[]> {
    // The refresh rate of the display in frames per second.
    public static final float FREQUENCY = 59.7f;

    private Observer<? super Integer[]> observer;

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

    void renderScanLine() {


        observer.onNext(null);
    }

    @Override
    protected void subscribeActual(Observer<? super Integer[]> observer) {
        this.observer = observer;
    }
}
