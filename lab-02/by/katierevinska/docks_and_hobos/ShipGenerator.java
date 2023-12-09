package by.katierevinska.docks_and_hobos;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class ShipGenerator implements Runnable {
    List<String> cargoTypes;
    private Long SHIP_CAPACITY_MIN;
    private Long SHIP_CAPACITY_MAX;
    private Long GENERATION_TIME;
    private final Timer timer;

    public ShipGenerator() {
        this.timer = new Timer();
    }

    ShipGenerator setGeneratingTime(Long time){
        GENERATION_TIME = time;
        return this;
    }
    ShipGenerator setShipCapacityMin(Long capacity){
        SHIP_CAPACITY_MIN = capacity;
        return this;
    }
    ShipGenerator setShipCapacityMax(Long capacity){
        SHIP_CAPACITY_MAX = capacity;
        return this;
    }
    ShipGenerator setCargoType(List<String> cargoTypes){
        this.cargoTypes = cargoTypes;
        return this;
    }
    Long getGeneratingTime(){
        return GENERATION_TIME;
    }
    final private TimerTask task = new TimerTask() {
        public void run() {
            Long shipCapacity = ThreadLocalRandom.current().nextLong(SHIP_CAPACITY_MIN, SHIP_CAPACITY_MAX + 1);
            int cargoType = ThreadLocalRandom.current().nextInt(0, cargoTypes.size());
            System.out.println("Ship generator send ship with type " + cargoType + " and capacity " + shipCapacity + "to tunnel");
            Process.getInstance().tunnel.setShip(new Ship(cargoTypes.get(cargoType), shipCapacity));
        }
    };
    @Override
    public void run(){
        timer.scheduleAtFixedRate(task, 0, getGeneratingTime());
    }
}
