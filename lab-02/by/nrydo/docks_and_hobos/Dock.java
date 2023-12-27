package by.nrydo.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dock implements Runnable {
    private final int unloadingSpeed;
    private final int dockCapacity;
    private final int[] currentCargoStore;
    private final Tunnel tunnel;
    private final int maxBatchCount = 1;
    private final Semaphore cargoSemaphore;
    private final Lock stealingLock;
    private int unloadThisSecond;
    private int unloadBatchCount;
    private final Logger logger;

    public Dock(Tunnel tunnel) {
        this.tunnel = tunnel;
        unloadingSpeed = ConfigReader.getInstance().getUnloadingSpeed();
        dockCapacity = ConfigReader.getInstance().getDockCapacity();
        currentCargoStore = new int[ConfigReader.getInstance().getCargoTypes().length];
        cargoSemaphore = new Semaphore(0);
        stealingLock = new ReentrantLock();
        unloadThisSecond = 0;
        unloadBatchCount = 0;
        logger = createLogger();
    }

    private Logger createLogger() {
        Logger logger = Logger.getLogger(Dock.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("log/dock.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create logger", e);
        }
        return logger;
    }

    @Override
    public void run() {
        while (true) {
            Ship ship = tunnel.getShip();
            unloadCargo(ship);
        }
    }

    public int steal() {
        try {
            cargoSemaphore.acquire();
            stealingLock.lock();
            List<Integer> cargos = new ArrayList<>();
            for (int i = 0; i < currentCargoStore.length; i++) {
                if (currentCargoStore[i] > 0) {
                    cargos.add(i);
                }
            }
            int cargo = cargos.get((new Random()).nextInt(cargos.size()));
            currentCargoStore[cargo]--;
            stealingLock.unlock();
            return cargo;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void unloadCargo(Ship ship) {
        while (ship.getStore() > 0) {
            try {
                int cargo = currentCargoStore[ship.getCargoType()];
                int batchSize = ship.take(getBatchSize());
                int cargoToUnload = Math.min(batchSize, dockCapacity - cargo);
                cargoSemaphore.release(batchSize);
                currentCargoStore[ship.getCargoType()] = cargo + cargoToUnload;
                logCargoUnloading(cargoToUnload, ConfigReader.getInstance().getCargoTypes()[ship.getCargoType()]);
                Thread.sleep(999L / maxBatchCount + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getBatchSize() {
        if (unloadBatchCount == 0) {
            unloadBatchCount = maxBatchCount;
            unloadThisSecond = unloadingSpeed;
        }
        int result = (unloadThisSecond + unloadBatchCount / 2) / unloadBatchCount;
        unloadThisSecond -= result;
        unloadBatchCount--;
        return result;
    }

    private void logCargoUnloading(int cargoToUnload, String cargoType) {
        logger.info(String.format("Unloaded cargo: quantity=%d, type=%s", cargoToUnload, cargoType));
    }
}