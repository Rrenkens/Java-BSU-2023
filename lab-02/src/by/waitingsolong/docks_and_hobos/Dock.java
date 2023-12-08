package by.waitingsolong.docks_and_hobos;

import by.waitingsolong.docks_and_hobos.helpers.CargoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Dock implements Runnable {
    private static final Logger logger = LogManager.getLogger(Dock.class);
    private final int unloading_speed;
    private final int dock_capacity;
    private volatile Map<CargoType, Integer> storage = new ConcurrentHashMap<>();
    private Tunnel tunnel;
    private long recallDelay;
    private ReentrantLock shipLock;
    private Condition unloadingIsFinished;

    public Dock(int unloadingSpeed, int dockCapacity, Tunnel tunnel, long recallDelay) {
        this.unloading_speed = unloadingSpeed;
        this.dock_capacity = dockCapacity;
        this.tunnel = tunnel;
        this.recallDelay = recallDelay;
        for (CargoType cargoType : CargoType.values()) {
            storage.put(cargoType, 0);
        }
    }

    @Override
    public void run() {
        while (true) {
            logger.info("Dock calls");
            Optional<Ship> call = tunnel.call(this);
            if (call.isPresent()) {
                logger.info("There is a response");
                processShip(call.get());
            } else {
                logger.info("There is no response, wait a little...");
                try {
                    Thread.sleep(recallDelay * 1000);
                } catch (InterruptedException e) {
                    logger.error("Dock is interrupted during sleep", e);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }


    public void processShip(Ship ship) {
        logger.info("Dock ready for ship unloading");
        ReentrantLock wakeUpLock = ship.getWakeUpLock();
        wakeUpLock.lock();

        logger.info("Ship " + ship.getName() + " entered the dock");
        synchronized (ship) {
            unload(ship);

            logger.info("Ship " + ship.getName() + " leaves the dock");
            ship.notify();
        }
    }

    private void unload(Ship ship) {
        CargoType cargoType = ship.getCargoType();

        logger.info("Ship " + ship.getName() + " with " + cargoType.getName() + " is unloading...");
        while (true) {
            int unloaded = ship.unload(unloading_speed);
            if (unloaded == 0) {
                return;
            }

            int become  = storage.get(cargoType) + unloaded;
            if (become < dock_capacity) {
                storage.put(cargoType, become);
            } else {
                storage.put(cargoType, dock_capacity);
                logger.info(cargoType.getName() + " storage is full");
                return;
            }

            Thread.yield();

            logger.info("The ship " + ship.getName() + " is unloading...");
            try {
                Thread.sleep(recallDelay * 1000);
            } catch(InterruptedException e) {
                logger.error("Unloading of ship is interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
