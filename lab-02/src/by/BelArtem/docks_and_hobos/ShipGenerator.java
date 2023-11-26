package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.Random;

public class ShipGenerator implements Runnable{


    private final int generating_time;
    private final int ship_capacity_min;

    private final int ship_capacity_max;

    private final static String[] cargo_types = {"first", "second", "third"};

    private final static int size = cargo_types.length;

    private ArrayList<Ship> ships;

    public ShipGenerator(int generatingTime, int ship_capacity_min,
                         int ship_capacity_max, ArrayList<Ship> arr) {
        this.generating_time = generatingTime;
        this.ship_capacity_min = ship_capacity_min;
        this.ship_capacity_max = ship_capacity_max;
        ships = arr;
    }

    public int getGeneratingTime() {
        return generating_time;
    }


    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            //System.out.println(new StringBuffer("Cur size: ").append(ships.size()));
            if (!ships.isEmpty()) {
                System.out.println(ships.get(ships.size() - 1).getCargoType() +
                        "; capacity: " + ships.get(ships.size() - 1).getCapacity());
            }
            int cargoIndex = random.nextInt(size);
            String cargoName = cargo_types[cargoIndex];
            int capacity = ship_capacity_min +
                    random.nextInt(ship_capacity_max - ship_capacity_min + 1);
            Ship ship = new Ship(capacity, cargoName);
            this.ships.add(ship);
            try {
                Thread.sleep(generating_time * 1000L);
            } catch (InterruptedException e) {
                System.out.println("During ship generation something went wrong");
                throw new RuntimeException(e);
            }
        }
    }




}
