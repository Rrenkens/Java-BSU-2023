package by.ullliaa.docks_and_hobos.actors;

import by.ullliaa.docks_and_hobos.actors.ships.Ship;
import by.ullliaa.docks_and_hobos.utilities.Controller;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dock implements Runnable {
    int id;
    public final int unloadingSpeed;
    final int[] dockCapacity;
    final AtomicIntegerArray currentCount;
    private static final Logger logger = Logger.getLogger(Dock.class.getName());

    public Dock(int unloadingSpeed, int[] dockCapacity, int id) {
        if (unloadingSpeed < 0) {
            throw new IllegalArgumentException("Unloading speed less then zero");
        }

        if (dockCapacity == null) {
            throw new IllegalArgumentException("Dock capacity is null");
        }

        for (int elem : dockCapacity) {
            if (elem < 0) {
                throw new IllegalArgumentException("Count of product should be positive");
            }
        }

        if (id < 0) {
            throw new IllegalArgumentException("Dock id should be positive");
        }

        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.id = id;
        currentCount = new AtomicIntegerArray(dockCapacity.length);
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Dock " + id + " start work");

        while (true) {
            try {
                UnloadShip(Controller.getController().getModel().getTunnel().takeShip());
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void UnloadShip(Ship ship) throws InterruptedException {
        if (ship == null) {
            return;
        }
        logger.log(Level.INFO, "Ship with cargo type " + ship.getShipType() + " and capacity " + ship.getShipCapacity() + " now in dock " + id);

        int index = Controller.getController().getModel().getCargoTypes().getType(ship.getShipType());
        int needCargo = dockCapacity[index] - currentCount.get(index);
        currentCount.addAndGet(index, Math.min(ship.getShipCapacity(), needCargo));

        if (ship.getShipCapacity() > needCargo) {
            logger.log(Level.INFO, (ship.getShipCapacity() - needCargo) + " kilos of " + ship.getShipType() + " was thrown out");
        }

        Thread.sleep( unloadingSpeed * 1000L);
    }

    public static Logger getLogger() {
        return logger;
    }

    public synchronized boolean stealProduct (int product) {
        if (currentCount.get(product) == 0) {
            return false;
        } else {
            currentCount.decrementAndGet(product);
            return true;
        }
    }
}
