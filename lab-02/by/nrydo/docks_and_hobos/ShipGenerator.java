package by.nrydo.docks_and_hobos;

import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ShipGenerator implements Runnable {
    private final Tunnel tunnel;
    private final Logger logger;

    public ShipGenerator(Tunnel tunnel) {
        this.tunnel = tunnel;
        this.logger = createLogger();
    }

    private Logger createLogger() {
        Logger logger = Logger.getLogger(ShipGenerator.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("log/ship_generator.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create logger", e);
        }
        return logger;
    }

    @Override
    public void run() {
        ConfigReader config = ConfigReader.getInstance();
        while (true) {
            try {
                Ship ship = generateShip();
                tunnel.addShip(ship);
                logShipGeneration(ship);
                Thread.sleep(config.getGeneratingTime() * 1000L);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Thread interrupted", e);
            }
        }
    }

    private Ship generateShip() {
        Random random = new Random();
        ConfigReader config = ConfigReader.getInstance();
        int minCapacity = config.getShipCapacityMin();
        int maxCapacity = config.getShipCapacityMax();
        int capacity = random.nextInt(maxCapacity - minCapacity + 1) + minCapacity;
        int cargoType = random.nextInt(config.getCargoTypes().length);
        return new Ship(capacity, cargoType);
    }

    private void logShipGeneration(Ship ship) {
        String message = String.format("Generated ship: capacity=%d, cargoType=%d", ship.getStore(), ship.getCargoType());
        logger.info(message);
    }
}