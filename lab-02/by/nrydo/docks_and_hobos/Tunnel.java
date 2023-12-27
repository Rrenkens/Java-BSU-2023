package by.nrydo.docks_and_hobos;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Tunnel {
    private final Semaphore placeSemaphore;
    private final Semaphore shipSemaphore;
    private final Queue<Ship> ships;
    private final Logger logger;

    public Tunnel() {
        ConfigReader config = ConfigReader.getInstance();
        placeSemaphore = new Semaphore(config.getMaxShips());
        shipSemaphore = new Semaphore(0);
        ships = new ConcurrentLinkedQueue<>();
        logger = createLogger();
    }

    private Logger createLogger() {
        Logger logger = Logger.getLogger(Tunnel.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("log/tunnel.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create logger", e);
        }
        return logger;
    }

    public void addShip(Ship ship) {
        if (placeSemaphore.tryAcquire()) {
            ships.add(ship);
            shipSemaphore.release();
            logShipAdded(ship);
        }
    }

    public Ship getShip() {
        try {
            shipSemaphore.acquire();
            Ship ship = ships.poll();
            placeSemaphore.release();
            logShipRemoved(ship);
            return ship;
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Thread interrupted", e);
            throw new RuntimeException(e);
        }
    }

    private void logShipAdded(Ship ship) {
        logger.info(String.format("Ship added to the tunnel: capacity=%d, cargoType=%s", ship.getStore(), ship.getTypeName()));
    }

    private void logShipRemoved(Ship ship) {
        logger.info(String.format("Ship removed from the tunnel: capacity=%d, cargoType=%s", ship.getStore(), ship.getTypeName()));
    }
}