package by.MikhailShurov.docks_and_hobos;

import java.util.*;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable {
    private static final Logger logger = LoggerUtil.getLogger();
    Tunnel tunnel_;
    double shipCapacity = 6;
    ArrayList<String> cargoTypes = new ArrayList<>(List.of("cocaine", "heroin", "marijuana", "pineapple"));
    int generationType = 1000;
    public ShipGenerator(Tunnel tunnel) {
        tunnel_ = tunnel;
    }
    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            int index = random.nextInt(cargoTypes.size());
            Ship ship = new Ship(shipCapacity, cargoTypes.get(index));
            logger.info("Generated new ship\n");
            System.out.println("SHIP GENERATOR: Generated new ship");
            tunnel_.addShip(ship);
            try {
                Thread.sleep(generationType);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}