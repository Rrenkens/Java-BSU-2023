package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class ShipGenerator implements Runnable{
    private final int generationTime;
    private final Timer timer;
    private final int shipCapacityMin;
    private final int shipCapacityMax;
    private final List<String> cargoTypes;

    final private TimerTask task = new TimerTask() {
        public void run() {
            Controller.getController().getModel().getTunel().tryAddShipToTunel
                    (new Ship(ThreadLocalRandom.current().nextInt(shipCapacityMin, shipCapacityMax + 1),
                   cargoTypes.get(ThreadLocalRandom.current().nextInt( cargoTypes.size()))));
        }
    };

    public ShipGenerator(int generationTime, int shipCapacityMin, int shipCapacityMax,
                         @NotNull List<String> cargoTypes) {
        if (generationTime <= 0){
            throw new IllegalArgumentException("Invalid period");
        }
        this.generationTime = generationTime;
        this.timer = new Timer();

        if (shipCapacityMax - shipCapacityMin < 0){
            throw new IllegalArgumentException("Max capacity less then min");
        }
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;

        if (cargoTypes.isEmpty()) {
            throw new IllegalArgumentException("Cargo types array is empty");
        }
        if (cargoTypes.contains(null)) {
            throw new IllegalArgumentException("Cargo types array contains null");
        }
        this.cargoTypes = cargoTypes;
    }

    @Override
    public void run(){
        timer.scheduleAtFixedRate (task, 0, generationTime * 1000L);
    }
}
