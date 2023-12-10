package by.lamposhka.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tunnel {
    private static final Logger logger = Logger.getLogger("logger");
    private Queue<Ship> ships = new ArrayDeque<>();
    int maxShips;

    public Tunnel(int maxShips) {
        this.maxShips = maxShips;
    }
    synchronized public void add(Ship ship) {
        if (ships.size() == ProgramArguments.MAX_SHIPS) {
            logger.log(Level.WARNING, "ship sank");
            return;
        }
        ships.add(ship);
        notifyAll();
        logger.log(Level.INFO, "ship added to tunnel: " + ships.size());
    }

    synchronized public Ship get() throws InterruptedException {
        while (ships.isEmpty()) {
            Thread.sleep(10);
            logger.log(Level.INFO, "no ships in the tunnel:"  + ships.size());
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ship removed" + ships.size());
        return ships.remove();
    }
}
