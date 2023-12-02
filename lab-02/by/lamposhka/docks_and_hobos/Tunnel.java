package by.lamposhka.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.Queue;

public class Tunnel {
    private Queue<Ship> ships = new ArrayDeque<>();

    synchronized public void add(Ship ship) {
        if (ships.size() == ProgramArguments.MAX_SHIPS) {
            return;
        }
        ships.add(ship);
    }

    synchronized public Ship next() throws InterruptedException {
        if (ships.isEmpty()) {
            //something
            return null;
        }
        return ships.remove();
    }
}
