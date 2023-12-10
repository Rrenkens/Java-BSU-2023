package by.Dzenia.docks_and_hobos.Persons;

import java.util.*;

public class Tunnel {

    private Queue<Ship> queue = new LinkedList<>();
    private int maxShips;

    public Tunnel(int maxShips) {
        this.maxShips = maxShips;
    }

    public synchronized void addShip(Ship ship) {
        if (queue.size() == maxShips) {
            return;
        }
        queue.add(ship);
        notify();
    }
}
