package by.waitingsolong.docks_and_hobos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ShipGenerator implements Runnable {
    private static final Logger logger = LogManager.getLogger(ShipGenerator.class);
    private int generating_time;
    private int ship_capacity_min;
    private int ship_capacity_max;
    private Tunnel tunnel;
    private static Random random = new Random();

    public ShipGenerator(int generating_time, int ship_capacity_min, int ship_capacity_max, Tunnel tunnel) {
        this.generating_time = generating_time;
        this.ship_capacity_min = ship_capacity_min;
        this.ship_capacity_max = ship_capacity_max;
        this.tunnel = tunnel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(generating_time * 1000);
                int capacity = ship_capacity_min + random.nextInt(ship_capacity_max - ship_capacity_min + 1);
                CargoType cargoType = CargoType.getRandom();
                Ship ship = new Ship(capacity, cargoType, tunnel);
                logger.info("Ship " + ship.getName() + " is on the horizon...");
                ship.start();
            } catch (InterruptedException e) {
                logger.error("Ship generator is interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
