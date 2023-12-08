package by.Lenson423.docks_and_hobos.main_actors;

import by.Lenson423.docks_and_hobos.utilities.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

import static java.lang.Math.min;
import static java.util.logging.Level.CONFIG;
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
        for (var capacity : dockCapacity) {
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity is negative");
            }
        }
        this.dockCapacity = dockCapacity;
        this.currentCount = new AtomicIntegerArray(dockCapacity.length);
    }

    public void getFromShip(@NotNull Ship ship) throws InterruptedException {
        int index = Controller.getController().getModel().getCargoTypes().getByName(ship.getShipType());
        int current = currentCount.addAndGet(index,
                min(ship.getShipCapacity(), dockCapacity[index] - currentCount.get(index)));
        logger.log(CONFIG, "Product " + ship.getShipType() + " in count of " + current + " now in dock");
        Thread.sleep((current - ship.getShipCapacity()) / unloadingSpeed * 1000L);
    }

    public synchronized boolean stealProduct(int product) {
        try {
            if (currentCount.get(product) == 0) {
                return false;
            }
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid product id");
        }
        currentCount.decrementAndGet(product);
        logger.log(CONFIG, "Product with id " + product + "was stolen");
        return true;
    }

    @Override
    public void run() {
        logger.log(INFO, "Dock start working");
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
