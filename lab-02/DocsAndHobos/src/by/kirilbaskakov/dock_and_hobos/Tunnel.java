package by.kirilbaskakov.dock_and_hobos;

import java.util.LinkedList;
import java.util.Queue;

public class Tunnel {
    private int maxShips;
    private Queue<Ship> ships;

    public Tunnel(int maxShips) {
        if (maxShips < 0) {
            MyLogger.getInstance().error("Ships less than 0");
            throw new IllegalArgumentException("Ships less than 0");
        }
        this.maxShips = maxShips;
        this.ships = new LinkedList<>();
    }

    public synchronized void addShip(Ship ship) {
        if (ships.size() < maxShips) {
            ships.add(ship);
            MyLogger.getInstance().info("Ship " + ship.getId() + " added to the tunnel.");
        } else {
            MyLogger.getInstance().info("Ship " + ship.getId() + " sunk. Tunnel is full.");
        }
    }

    public synchronized Ship getFirstShip() {
        return ships.peek();
    }

    public synchronized void removeFirstShip() {
        Ship ship = ships.poll();
        if (ship != null) {
            MyLogger.getInstance().info("Ship " + ship.getId() + " removed from the tunnel.");
        }
    }

    public synchronized boolean isEmpty() {
        return ships.isEmpty();
    }
}