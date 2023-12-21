package by.Dzenia.docks_and_hobos.RunnableObjects;

import by.Dzenia.docks_and_hobos.Controller.Model;
import by.Dzenia.docks_and_hobos.CustomLogger;
import by.Dzenia.docks_and_hobos.Persons.Ship;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Dock implements Runnable {
    private final HashMap<String, Integer> dockCapacity;
    private final HashMap<String, Integer> currentWeight = new HashMap<>();
    private final Logger logger = CustomLogger.getLogger("all");
    private final int speed;
    private final Model model;

    public Dock(int speed, HashMap<String, Integer> dockCapacity, Model model) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed should be positive");
        }
        for (Integer capacity: dockCapacity.values()) {
            if (capacity < 0) {
                throw new IllegalArgumentException("Cargo capacity should be none negative");
            }
        }
        this.dockCapacity = dockCapacity;
        this.speed = speed;
        this.model = model;
        for (String typeCargo: dockCapacity.keySet()) {
            currentWeight.put(typeCargo, 0);
        }
    }

    public synchronized Integer stealCargo(String cargo, Integer count) {
        if (!currentWeight.containsKey(cargo)) {
            return 0;
        }
        Integer was = currentWeight.get(cargo);
        currentWeight.put(cargo, max(was - count, 0));
        return was - currentWeight.get(cargo);
    }


    private void shipUnloading(Ship ship) throws InterruptedException {
        logger.log(Level.INFO, "Ship come to dock cargo=" + ship.getCargo().getType() + ", weight=" + ship.getWeight());
        Thread.sleep((int)(ship.getWeight() / speed) * 1000);
        synchronized (currentWeight) {
            Integer weight = currentWeight.get(ship.getCargo().getType());
            currentWeight.put(ship.getCargo().getType(), min(
                    dockCapacity.get(ship.getCargo().getType()),
                    weight + (Integer) ship.getWeight()
            ));
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                shipUnloading(model.getTunnel().getShip());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
