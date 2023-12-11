package by.lamposhka.docks_and_hobos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dock implements Runnable {
    int unloadingSpeed;
    ConcurrentHashMap<String, Integer> storageTaken = new ConcurrentHashMap<>();
    HashMap<String, Integer> storageCapacity = new HashMap<>();
    private static final Logger logger = Logger.getLogger("DockLogger");

    public Dock(int unloadingSpeed, Map<String, Integer> storageCapacity) {
        this.unloadingSpeed = unloadingSpeed;
        for (var cargoType : storageCapacity.keySet()) {
            this.storageTaken.put(cargoType, 0);
            this.storageCapacity.put(cargoType, storageCapacity.get(cargoType));
        }
    }

    public synchronized void unload(Ship ship) throws InterruptedException {
        logger.log(Level.INFO, "DOCK " + this.hashCode() + " STARTED UNLOADING A SHIP");
        Thread.sleep(50L * ship.getLoad());
        if (storageCapacity.keySet().contains(ship.getCargoType())) {
            if (!storageTaken.get(ship.getCargoType()).equals(storageCapacity.get(ship.getCargoType()))) {
                storageTaken.put(ship.getCargoType(), Integer.valueOf(Math.min(storageCapacity.get(ship.getCargoType()).intValue(),
                        storageTaken.get(ship.getCargoType()).intValue() + ship.getLoad())));
            }
        }
        for (var i : storageTaken.keySet()) {
            System.out.println(i + storageTaken.get(i));
        }
        logger.log(Level.INFO, "DOCK " + this.hashCode() + " FINISHED UNLOADING A SHIP");
    }

    public synchronized boolean steal(String cargo)  {
        if (storageTaken.get(cargo) != 0) {
            storageTaken.put(cargo, storageTaken.get(cargo) - 1);
            logger.log(Level.INFO, cargo + " STOLEN");
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (var i : storageTaken.keySet()) {
                System.out.println(i + storageTaken.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                unload(Controller.getInstance().getTunnel().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
