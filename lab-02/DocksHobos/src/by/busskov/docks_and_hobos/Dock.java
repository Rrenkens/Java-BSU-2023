package by.busskov.docks_and_hobos;

import java.time.LocalTime;
import java.util.*;

public class Dock implements Runnable {
    public Dock(
            int unloadingSpeed,
            int capacity,
            ArrayList<String> cargoTypes,
            Tunnel tunnel
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
                System.out.println(ship.toString() + "     " + LocalTime.now());
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
                value += ship.takeGoods(unloadingSpeed);
                if (value > capacity) {
                    value = capacity;
                }
                warehouse.put(shipCargoType, value);

                System.out.println(warehouse.toString() + "   " + LocalTime.now());
            }
        }, 1000, 1000);
        while (!ship.isEmpty()) {

        }
        timer.cancel();
    }

    private final int unloadingSpeed;
    private final int capacity;
    private final HashMap<String, Integer> warehouse;

    private final Tunnel tunnel;


    private class Hobo {

    }
}
