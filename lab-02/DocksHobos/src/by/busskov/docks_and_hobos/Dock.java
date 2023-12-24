package by.busskov.docks_and_hobos;

import java.util.*;
import java.util.logging.Level;

public class Dock implements Runnable {
    Timer timer;
    private final int unloadingSpeed;
    private final int capacity;
    private final Warehouse warehouse;
    private final BayLogger logger;
    private final HobosGroup hobosGroup;

    private final Tunnel tunnel;
    public Dock(
            int unloadingSpeed,
            int capacity,
            ArrayList<String> cargoTypes,
            Tunnel tunnel,
            BayLogger logger,
            int numberOfHobos,
            int eatingTime,
            int stealingTime,
            HashMap<String, Integer> necessaryIngredients
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

        warehouse = new Warehouse(cargoTypes);
        hobosGroup = new HobosGroup(numberOfHobos, eatingTime, stealingTime, necessaryIngredients, warehouse, logger);
    }
    @Override
    public void run() {
        Thread hobosThread = new Thread(hobosGroup, "Hobos Group");
        hobosThread.start();

        while(true) {
            if (!tunnel.isEmpty()) {
                Ship ship = tunnel.getShip();
                logger.log(Level.INFO, "Dock took new ship: {0}", ship);
                processShip(ship);
            }
        }
    }

    private void processShip(Ship ship) {
        timer = new Timer();
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

    public static class Warehouse {
        private final HashMap<String, Integer> warehouse;
        public Warehouse(ArrayList<String> types) {
            warehouse = new HashMap<>(types.size());
            for (String type : types) {
                warehouse.put(type, 0);
            }
        }

        public synchronized void put(String value, Integer number) {
            warehouse.put(value, number);
        }

        public synchronized boolean steal(String value) {
            if (warehouse.get(value) == 0) {
                return false;
            }
            int currentAmount = warehouse.get(value);
            --currentAmount;
            warehouse.put(value, currentAmount);
            return true;
        }

        public synchronized Integer get(String value) {
            return warehouse.get(value);
        }
    }
}
