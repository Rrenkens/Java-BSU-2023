package by.Lenson423.docks_and_hobos.main_actors;

import by.Lenson423.docks_and_hobos.utilities.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

import static java.lang.Math.min;
import static java.util.logging.Level.ALL;
import static java.util.logging.Level.INFO;

public class Dock implements Runnable {
    final int unloadingSpeed;
    final int[] dockCapacity;
    final AtomicIntegerArray currentCount;
    private static final Logger logger = Logger.getLogger(Dock.class.getName());

    public Dock(int unloadingSpeed, int @NotNull [] dockCapacity) {
        if (unloadingSpeed < 0) {
            throw new IllegalArgumentException("Unloading speed less then 0");
        }
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.currentCount = new AtomicIntegerArray(dockCapacity.length);
    }

    public void getFromShip(@NotNull Ship ship) throws InterruptedException {
        int index = Controller.getController().getModel().getCargoTypes().getByName(ship.getShipType());
        int current = currentCount.addAndGet(index,
                min(ship.getShipCapacity(), dockCapacity[index] - currentCount.get(index)));
        logger.log(INFO, "Product " + ship.getShipType() + " in count of " + current + " now in dock");
        Thread.sleep((current - ship.getShipCapacity()) / unloadingSpeed * 1000L);
    }

    public synchronized boolean stealProduct(int product) {
        if (currentCount.get(product) == 0) {
            return false;
        }
        currentCount.decrementAndGet(product);
        logger.log(INFO, "Product with id " + product + "was stealed");
        return true;
    }

    @Override
    public void run() {
        logger.log(ALL, "Dock start working");
        while (true) {
            try {
                getFromShip(Controller.getController().getModel().getTunel().getPassedShip());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
