package by.waitingsolong.docks_and_hobos;

import java.util.concurrent.atomic.AtomicInteger;

import by.waitingsolong.docks_and_hobos.helpers.CargoType;
import by.waitingsolong.docks_and_hobos.helpers.NameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ship implements Runnable {
    private int capacity;
    private final CargoType cargoType;
    private final String name;
    private static final Logger logger = LogManager.getLogger(Ship.class);
    private static final AtomicInteger shipCount = new AtomicInteger(0);
    private Tunnel tunnel;
    private Thread thread;
    private final ReentrantLock wakeUpLock;

    public Ship(int capacity, CargoType cargoType, Tunnel tunnel) {
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.name = NameGenerator.generateName(shipCount.getAndIncrement());
        this.tunnel = tunnel;
        this.thread = new Thread(this);
        this.wakeUpLock = new ReentrantLock();
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        tunnel.enter(this);
        wakeUpLock.lock();
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.info("Ship " + name + " has sunk");
                Thread.currentThread().interrupt();
                return;
            }
            finally {
                wakeUpLock.unlock();
            }
        }
        logger.info("Ship " + this.name + " exit the tunnel");

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Ship " + name + " is interrupted in the dock", e);
                Thread.currentThread().interrupt();
            }
        }

        logger.info("Ship " + name + " disappears on the horizon");
        thread.interrupt();
    }

    public int unload(int units) {
        if (capacity < units) {
            int given = capacity;
            capacity = 0;
            return given;
        }
        else {
            capacity -= units;
            return units;
        }
    }

    public String getName() {
        return name;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public ReentrantLock getWakeUpLock() { return wakeUpLock; }
}
