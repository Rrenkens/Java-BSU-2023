package by.lamposhka.docks_and_hobos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dock implements Runnable{
    int unloadingSpeed;
    ConcurrentHashMap<String, Integer> storageCapacity = new ConcurrentHashMap<>();
    HashMap<String, Integer> storageTaken = new HashMap<>();
    private static final Logger logger = Logger.getLogger("DockLogger");

    public Dock(int unloadingSpeed, Map<String, Integer> storageCapacity) {
        this.unloadingSpeed = unloadingSpeed;
        for (var cargoType: storageCapacity.keySet()) {
            this.storageTaken.put(cargoType, 0);
            this.storageCapacity.put(cargoType, storageCapacity.get(cargoType));
        }
    }

    public void unload(Ship ship) throws InterruptedException {
        logger.log(Level.INFO, "DOCK " + this.hashCode() + " STARTED UNLOADING A SHIP");
        Thread.sleep(50L * ship.getLoad());
        if (storageCapacity.keySet().contains(ship.getCargoType())) {
            if (!storageTaken.get(ship.getCargoType()).equals(storageCapacity.get(ship.getCargoType()))) {
                storageTaken.put(ship.getCargoType(), Integer.valueOf(Math.min(storageCapacity.get(ship.getCargoType()).intValue(),
                        storageTaken.get(ship.getCargoType()).intValue() + ship.getLoad())));
            }
        }
        for (var i: storageTaken.keySet()) {
            System.out.println(i + storageTaken.get(i));
        }
        logger.log(Level.INFO, "DOCK " + this.hashCode() + " FINISHED UNLOADING A SHIP");
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
