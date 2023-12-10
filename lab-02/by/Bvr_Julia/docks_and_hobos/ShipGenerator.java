package by.Bvr_Julia.docks_and_hobos;

import java.util.List;

public class ShipGenerator implements Runnable {
    public final Long generating_time;
    private final Long ship_capacity_min;
    private final Long ship_capacity_max;
    private final List<String> cargo_types;
    private Thread thread;

    private volatile List<Ship> ships;

    public Thread getThread() {
        return thread;
    }

    ShipGenerator(List<Ship> ships, Long generating_time, Long ship_capacity_min, Long ship_capacity_max, List<String> cargo_types) {
        this.generating_time = generating_time;
        this.ship_capacity_min = ship_capacity_min;
        this.ship_capacity_max = ship_capacity_max;
        this.cargo_types = cargo_types;
        this.ships = ships;
        thread = new Thread(this, "ShipGenerator thread");
        System.out.println("ShipGenerator was made up. " + cargo_types);
    }

    public Ship generate() {
        Long capacity = Randomizer.generate(ship_capacity_min, ship_capacity_max);
        int cargo = Randomizer.generate((long) (0), (long) cargo_types.size() - 1).intValue();
        System.out.println("ShipGenerator is trying to make ship. " + Long.toString(capacity) + cargo_types.get(cargo));
        return new Ship(capacity, cargo_types.get(cargo));
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) / 1000 < Main.time) {
            ships.add(generate());
            System.out.println("Ship was added to the tonnel. ");
            try {
                Thread.sleep(generating_time.intValue() * 1000);
            } catch (InterruptedException e) {
                System.out.println("ShipGenerator has been interrupted\n");
            }
        }
        thread.interrupt();
    }
}
