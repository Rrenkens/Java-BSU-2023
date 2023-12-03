package by.waitingsolong.docks_and_hobos;

import java.util.concurrent.atomic.AtomicInteger;

import by.waitingsolong.docks_and_hobos.helpers.NameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship implements Runnable {
    private int capacity;
    private CargoType cargoType;
    private String name;
    private static final Logger logger = LogManager.getLogger(Ship.class);
    private static final AtomicInteger shipCount = new AtomicInteger(0);
    private Tunnel tunnel;
    private Dock dock;
    private Thread thread;

    public Ship(int capacity, CargoType cargoType, Tunnel tunnel) {
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.name = String.valueOf(NameGenerator.generateName(shipCount.getAndIncrement()));
        this.tunnel = tunnel;
        this.thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    public void interrupt() {
        thread.interrupt();
    }

    @Override
    public void run() {
        tunnel.enter(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.info("Ship " + name + " has sunk");
                Thread.currentThread().interrupt();
                return;
            }
        }
        dock.process(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Ship " + name + " is interrupted in the dock", e);
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Ship " + name + "  disappears on the horizon");
        thread.interrupt();
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    public String getName() {
        return name;
    }
}
