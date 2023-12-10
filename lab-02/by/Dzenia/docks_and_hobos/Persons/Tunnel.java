package by.Dzenia.docks_and_hobos.Persons;

import java.util.*;

public class Tunnel {

    private final Queue<Ship> queue = new LinkedList<>();
    private final int maxShips;

    public Tunnel(int maxShips) {
        this.maxShips = maxShips;
    }

    public synchronized void addShip(Ship ship) {
        if (queue.size() == maxShips) {
            System.out.println("Ship go down with cargo=" + ship.getCargo().getType() + ", weight=" + ship.getWeight());
            return;
        }
        queue.add(ship);
        notify();
    }

    public synchronized Ship getShip() throws InterruptedException {
        if (queue.isEmpty()) {
            wait();
        }
        return queue.remove();
     }
}
