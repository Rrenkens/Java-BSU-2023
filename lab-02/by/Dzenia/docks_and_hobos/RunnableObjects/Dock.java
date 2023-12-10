package by.Dzenia.docks_and_hobos.RunnableObjects;

import by.Dzenia.docks_and_hobos.Controller.Model;
import by.Dzenia.docks_and_hobos.Persons.Ship;

import java.util.HashMap;

import static java.lang.Math.min;

public class Dock implements Runnable {
    private final HashMap<String, Integer> dockCapacity;
    private final HashMap<String, Integer> currentWeight = new HashMap<>();
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

    void shipRazgruzka(Ship ship) throws InterruptedException {
        System.out.println("Ship come to dock, with=" + ship.getCargo().getType() + ", weight=" + ship.getWeight());
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
                shipRazgruzka(model.getTunnel().getShip());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
