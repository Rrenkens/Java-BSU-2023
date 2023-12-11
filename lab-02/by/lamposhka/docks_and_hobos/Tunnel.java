package by.lamposhka.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tunnel {
    private Queue<Ship> ships = new ArrayDeque<>();
    private static Logger logger = Logger.getLogger("TunnelLogger");
    int maxShips;

    public Tunnel(int maxShips) {
        this.maxShips = maxShips;
    }
    synchronized public void add(Ship ship) {
        if (ships.size() == maxShips) {
            logger.log(Level.WARNING, "SHIP SANK");
            return;
        }
        ships.add(ship);
        notifyAll();
        logger.log(Level.INFO, "SHIP ADDED TO TUNNEL: " + ships.size());
    }

    synchronized public Ship get() throws InterruptedException {
        while (ships.isEmpty()) {
            Thread.sleep(10);
            logger.log(Level.INFO, "NO SHIPS IN THE TUNNEL: "  + ships.size());
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.INFO, "SHIP REMOVED: " + ships.size());
        return ships.remove();
    }
}
