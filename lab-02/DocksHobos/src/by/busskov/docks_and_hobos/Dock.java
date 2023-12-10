package by.busskov.docks_and_hobos;

import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dock implements Runnable {
    public Dock(
            int unloadingSpeed,
            int capacity,
            ArrayList<String> cargoTypes,
            Tunnel tunnel,
            BayLogger logger
    ) {
        if (unloadingSpeed <= 0) {
            throw new IllegalArgumentException("Unloading speed must be positive");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (cargoTypes == null) {
            throw new NullPointerException("Cargo types mustn't be null");
        }
        if (cargoTypes.isEmpty()) {
            throw new IllegalArgumentException("Cargo types must have values");
        }
        this.unloadingSpeed = unloadingSpeed;
        this.capacity = capacity;
        this.tunnel = tunnel;
        this.logger = logger;

        warehouse = new HashMap<>(cargoTypes.size());
        for (String type : cargoTypes) {
            warehouse.put(type, 0);
        }
    }
    @Override
    public void run() {
        while(true) {
            if (!tunnel.isEmpty()) {
                Ship ship = tunnel.getShip();
                logger.log(Level.INFO, "Dock took new ship: {0}", ship);
                processShip(ship);
            }
        }
    }

    private void processShip(Ship ship) {
        Timer timer = new Timer();
        String shipCargoType = ship.getCargoType();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int value = warehouse.get(shipCargoType);
                int taken = ship.takeGoods(unloadingSpeed);
                logger.log(Level.ALL, "Dock took {0} {1} from ship", new Object[] {taken, shipCargoType});
                value += taken;
                if (value > capacity) {
                    value = capacity;
                }
                warehouse.put(shipCargoType, value);
            }
        }, 1000, 1000);
        while (!ship.isEmpty()) {

        }
        timer.cancel();
    }

    private final int unloadingSpeed;
    private final int capacity;
    private final HashMap<String, Integer> warehouse;
    private final BayLogger logger;

    private final Tunnel tunnel;


    private class HobosGroup implements Runnable {

        @Override
        public void run() {

        }
    }
    
}
