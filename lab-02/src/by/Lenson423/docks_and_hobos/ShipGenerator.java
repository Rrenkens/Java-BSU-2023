package by.Lenson423.docks_and_hobos;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShipGenerator {
    final int generationTime;
    final Timer timer;
    final int shipCapacityMin;
    final int shipCapacityMax;
    final List<String> cargoTypes;

    final private TimerTask task = new TimerTask() {
        public void run() {
            //return new Ship(ThreadLocalRandom.current().nextInt(shipCapacityMin, shipCapacityMax + 1),
              //      cargoTypes.get(ThreadLocalRandom.current().nextInt( cargoTypes.size())));
        }
    };

    public ShipGenerator(int generationTime, int shipCapacityMin, int shipCapacityMax, List<String> cargoTypes) {
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

        if (cargoTypes == null) {
            throw new IllegalArgumentException("Cargo types array is null");
        }
        if (cargoTypes.isEmpty()) {
            throw new IllegalArgumentException("Cargo types array is empty");
        }
        if (cargoTypes.contains(null)) {
            throw new IllegalArgumentException("Cargo types array contains null");
        }
        this.cargoTypes = cargoTypes;
    }

    public void startGenerating(){
        timer.scheduleAtFixedRate (task, 0, generationTime * 1000L);
    }
}
