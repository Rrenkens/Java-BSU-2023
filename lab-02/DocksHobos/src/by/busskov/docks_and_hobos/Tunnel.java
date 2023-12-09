package by.busskov.docks_and_hobos;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Tunnel {
    public Tunnel(int maxShips) {
        if (maxShips <= 0) {
            throw new IllegalArgumentException("max ships in tunnel must be > 0");
        }
        queue = new ConcurrentLinkedQueue<>();
        this.maxShips = maxShips;
    }

    public boolean isFull() {
        return maxShips == queue.size();
    }

    public synchronized void addShip(Ship ship) {
        if (!this.isFull()) {
            queue.add(ship);
        }
    }

    public synchronized Ship getShip() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("Tunnel is empty");
        }
        return queue.poll();
    }

    private final int maxShips;
    private final ConcurrentLinkedQueue<Ship> queue;
}
