package by.lamposhka.docks_and_hobos;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable {
    private int ship_capacity_min;
    private int ship_capacity_max;
    private ArrayList<String> cargo_types;
    private int generating_time;
    private static final Random random = new Random();
    private static final Logger logger = Logger.getLogger("shipGeneratorLogger");

    ShipGenerator(int ship_capacity_min, int ship_capacity_max, ArrayList<String> cargo_types, int generating_time) {
        this.ship_capacity_min = ship_capacity_min;
        this.ship_capacity_max = ship_capacity_max;
        this.cargo_types = cargo_types;
        this.generating_time = generating_time;
        logger.log(Level.INFO, "SHIP GENERATOR CREATED.");
    }

    public Ship generate() {
        logger.log(Level.INFO, "NEW SHIP CREATED.");
        return new Ship(cargo_types.get(random.nextInt(cargo_types.size())),
                random.nextInt(ship_capacity_max - ship_capacity_min + 1) + ship_capacity_min);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(generating_time * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Controller.getInstance().getTunnel().add(generate());
        }
    }
}
