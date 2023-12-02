package by.lamposhka.docks_and_hobos;

import java.util.HashMap;


public class Dock {
    int unloadingSpeed;
    boolean available = true;
    HashMap<String, Integer> storageCapacity;
    HashMap<String, Integer> storageTaken;

    public void unload(Ship ship) throws InterruptedException {
        available = false;
        Thread.sleep(50L * ship.getLoad());
        if (!storageTaken.get(ship.getCargoType()).equals(storageCapacity.get(ship.getCargoType()))) {
            storageTaken.put(ship.getCargoType(), Integer.valueOf(Math.min(storageCapacity.get(ship.getCargoType()).intValue(),
                    storageTaken.get(ship.getCargoType()).intValue() + ship.getLoad())));
        }
        available = true;
    }
}
