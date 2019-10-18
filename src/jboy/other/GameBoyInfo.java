package jboy.other;

import io.reactivex.Observable;
import io.reactivex.Observer;
import jboy.system.GameBoy;

public class GameBoyInfo extends Observable<GameBoyInfo> {
    private GameBoy gameBoy;
    private CpuInfo cpuInfo;
    private Observer<? super GameBoyInfo> observer;

    public GameBoyInfo(GameBoy gb) {
        this.gameBoy = gb;
        this.cpuInfo = new CpuInfo(this.gameBoy.getCpu());

        this.gameBoy.getCpu().subscribe(val -> {
            this.setCpuInfo(val);

            this.observer.onNext(this);
        });
    }

    @Override
    protected void subscribeActual(Observer<? super GameBoyInfo> observer) {
        this.observer = observer;
    }

    public CpuInfo getCpuInfo() {
        return this.cpuInfo;
    }

    public void setCpuInfo(CpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public MemoryInfo getMemoryInfo() {
        return new MemoryInfo(this.gameBoy.getMemory());
    }
}
