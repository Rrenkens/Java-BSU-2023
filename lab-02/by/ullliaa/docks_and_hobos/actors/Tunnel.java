package by.ullliaa.docks_and_hobos.actors;

import by.ullliaa.docks_and_hobos.actors.ships.Ship;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tunnel {
    public final int maxShips;
    final Queue<Ship> ships = new ArrayDeque<>();
    private static final Logger logger = Logger.getLogger(Tunnel.class.getName());

    public Tunnel(int maxShips) {
        if (maxShips <= 0) {
            throw new IllegalArgumentException("count of max ships on tunnel should be positive");
        }

        this.maxShips = maxShips;
        logger.log(Level.INFO, "Add tunnel which can contain maximum " + maxShips + " ships");
    }

    public boolean hasShips() {
        return !ships.isEmpty();
    }

    public synchronized void addShips(Ship ship) {
        if (ship == null) {
            throw new RuntimeException("ship is null");
        }

        if (ships.size() != maxShips) {
            ships.add(ship);
            notify();
        } else {
            logger.log(Level.INFO, "The tunnel is full. Ship with capacity " + ship.getShipCapacity() +
                    " and cargo type " +ship.getShipType() + " go down.");
        }
    }

    public synchronized Ship takeShip() throws InterruptedException {
        if (hasShips()) {
            wait();
        }
        return ships.poll();
    }

    public static Logger getLogger() {
        return logger;
    }
}
