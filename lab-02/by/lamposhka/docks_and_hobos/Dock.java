package by.lamposhka.docks_and_hobos;

import java.util.HashMap;


public class Dock implements Runnable{
    int unloadingSpeed;
    HashMap<String, Integer> storageCapacity = new HashMap<>();
    HashMap<String, Integer> storageTaken = new HashMap<>();

    public Dock() {
        for (var i: ) {
            storageTaken.put(i, 0);
            storageCapacity.put(i, 20);
        }
    }

    public void unload(Ship ship) throws InterruptedException {
        System.out.println("started unloading");
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
        System.out.println("ended unloading");
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
