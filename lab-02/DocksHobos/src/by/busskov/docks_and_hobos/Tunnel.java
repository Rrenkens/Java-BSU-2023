package by.busskov.docks_and_hobos;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tunnel {
    private final int maxShips;
    private final ConcurrentLinkedQueue<Ship> queue;
    private final BayLogger logger;
    public Tunnel(
            int maxShips,
            BayLogger logger
    ) {
        if (maxShips <= 0) {
            throw new IllegalArgumentException("max ships in tunnel must be > 0");
        }
        queue = new ConcurrentLinkedQueue<>();
        this.maxShips = maxShips;
        this.logger = logger;
    }

    public boolean isFull() {
        return maxShips == queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void addShip(Ship ship) {
        if (!this.isFull()) {
            queue.add(ship);
            logger.log(Level.ALL, "Tunnel got new ship: {0}; current state: {1}", new Object[]{ship, queue});
        } else {
            logger.log(Level.INFO, "Tunnel is full, can't take new ship");
        }
    }

    public synchronized Ship getShip() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("Tunnel is empty");
        }
        logger.log(Level.ALL, "One ship left tunnel, current state: {0}", queue);
        return queue.poll();
    }
}
