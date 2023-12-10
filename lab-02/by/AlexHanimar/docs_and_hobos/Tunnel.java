package by.AlexHanimar.docs_and_hobos;

import java.util.Optional;
import java.util.ArrayDeque;
import java.util.Queue;

import by.AlexHanimar.docs_and_hobos.Ship;

public class Tunnel {
    private final Queue<Ship> ships;
    private final int maxSize;

    public Tunnel(int maxSize) {
        this.maxSize = maxSize;
        ships = new ArrayDeque<>();
    }

    synchronized boolean AddShip(Ship ship) {
        if (ships.size() < maxSize) {
            ships.add(ship);
            return true;
        } else {
            return false;
        }
    }

    synchronized Optional<Ship> getShip() {
        return Optional.ofNullable(ships.poll());
    }
}
