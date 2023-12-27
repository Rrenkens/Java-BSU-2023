package by.nrydo.docks_and_hobos;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Tunnel {
    private final Semaphore placeSemaphore;
    private final Semaphore shipSemaphore;
    private final Queue<Ship> ships;

    public Tunnel() {
        placeSemaphore = new Semaphore(ConfigReader.getInstance().getMaxShips());
        shipSemaphore = new Semaphore(0);
        ships = new ConcurrentLinkedQueue<>();
    }

    public void addShip(Ship ship) {
        if (placeSemaphore.tryAcquire()) {
            ships.add(ship);
            shipSemaphore.release();
        }
    }

    public Ship getShip() {
        try {
            shipSemaphore.acquire();
            Ship ship = ships.poll();
            placeSemaphore.release();
            return ship;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
