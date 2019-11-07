package jboy.other;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import jboy.system.GameBoy;

public class GameBoyInfo extends Observable<GameBoyInfo> {
    private GameBoy gameBoy;
    private CpuInfo cpuInfo;
    private MemoryInfo memoryInfo;
    private Observer<? super GameBoyInfo> observer;

    private Disposable cpuSubscription;
    private Disposable memorySubscription;

    public GameBoyInfo(GameBoy gb) {
        this.gameBoy = gb;
        this.cpuInfo = new CpuInfo(this.gameBoy.getCpu());
        this.memoryInfo = new MemoryInfo(this.gameBoy.getMemory());

        this.cpuSubscription = this.gameBoy.getCpu().subscribe(val -> this.observer.onNext(this));
        this.memorySubscription = this.gameBoy.getMemory().subscribe(val -> this.observer.onNext(this));
    }

    @Override
    protected void subscribeActual(Observer<? super GameBoyInfo> observer) {
        this.observer = observer;
    }

    public CpuInfo getCpuInfo() {
        return this.cpuInfo;
    }

    public MemoryInfo getMemoryInfo() {
        return this.memoryInfo;
    }

    public void unsubscribe() {
        this.cpuSubscription.dispose();
        this.memorySubscription.dispose();
    }
}
