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

        warehouse = new ArrayList<>(Collections.nCopies(cargoTypes.size(), 0));
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
        int index = ship.getCargoType();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int currentValue = warehouse.get(index);
                currentValue += ship.takeGoods(unloadingSpeed);
                if (currentValue > capacity) {
                    currentValue = capacity;
                }
                warehouse.set(index, currentValue);

                System.out.println(warehouse.toString() + "   " + LocalTime.now());
            }
        }, 1000, 1000);
        while (!ship.isEmpty()) {

        }
        timer.cancel();
    }

    private final int unloadingSpeed;
    private final int capacity;
    private final ArrayList<Integer> warehouse;

    private final Tunnel tunnel;


    private class Hobo {

    }
}
