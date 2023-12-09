package by.katierevinska.docks_and_hobos.model;

import by.katierevinska.docks_and_hobos.Controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

class ShipGenerator implements Runnable {

    private Long SHIP_CAPACITY_MIN;
    private Long SHIP_CAPACITY_MAX;
    private Long GENERATION_TIME;
    private final Timer timer;

    private Long shipId = 100L;
    public ShipGenerator() {
        this.timer = new Timer();
    }

    public ShipGenerator setGeneratingTime(Long time){
        GENERATION_TIME = time;
        return this;
    }
    public ShipGenerator setShipCapacityMin(Long capacity){
        SHIP_CAPACITY_MIN = capacity;
        return this;
    }
    public ShipGenerator setShipCapacityMax(Long capacity){
        SHIP_CAPACITY_MAX = capacity;
        return this;
    }
    
    Long getGeneratingTime(){
        return GENERATION_TIME;
    }
    final private TimerTask task = new TimerTask() {
        public void run() {
            Long shipCapacity = ThreadLocalRandom.current().nextLong(SHIP_CAPACITY_MIN, SHIP_CAPACITY_MAX + 1);
            int cargoType = ThreadLocalRandom.current().nextInt(0, Controller.getInstance().getModel().getCargoTypes().size());
            Controller.getInstance().getModel().getTunnel().setShip(new Ship(Controller.getInstance().getModel().getCargoTypes().get(cargoType), shipCapacity, shipId++));
            System.out.println("Ship generator send ship " +(shipId-1)+" with type " + Controller.getInstance().getModel().getCargoTypes().get(cargoType) + " and capacity " + shipCapacity + "to tunnel");

        }
    };
    @Override
    public void run(){
        timer.scheduleAtFixedRate(task, 0, getGeneratingTime());
    }
}
