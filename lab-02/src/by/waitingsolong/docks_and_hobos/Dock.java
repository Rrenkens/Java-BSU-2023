package by.waitingsolong.docks_and_hobos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dock implements Runnable {
    private static final Logger logger = LogManager.getLogger(Dock.class);
    private int unloading_speed;
    private int dock_capacity;
    private Map<CargoType, Integer> storage = new ConcurrentHashMap<>();
    private Tunnel tunnel;
    private long waitingTime;

    public Dock(int unloadingSpeed, int dockCapacity, Tunnel tunnel, long waitingTime) {
        this.unloading_speed = unloadingSpeed;
        this.dock_capacity = dockCapacity;
        this.tunnel = tunnel;
        this.waitingTime = waitingTime;
    }

    @Override
    public void run() {
        while (true) {
            logger.info("The dock calls the ship");
            if (tunnel.call(this)) {
                logger.info("There is a response");
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.error("Dock is interrupted", e);
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            } else {
                logger.info("There is no response, wait a little...");
                try {
                    Thread.sleep(waitingTime * 1000);
                } catch (InterruptedException e) {
                    logger.error("Dock is interrupted during sleep", e);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }


    public void process(Ship ship) {
        logger.info("Ship " + ship.getName() + " entered the dock");
        unload(ship);
        synchronized (ship) {
            logger.info("Ship " + ship.getName() + " leaves the dock");
            ship.notify();
        }
        synchronized (this) {
            notify();
        }
    }

    private void unload(Ship ship) {
        logger.info("The ship " + ship.getName() + " is unloading...");
        try {
            Thread.sleep( waitingTime / 2 * 1000);
        } catch(InterruptedException e) {
            logger.error("Unloading of ship is interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
