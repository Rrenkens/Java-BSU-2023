package by.Lenson423.docks_and_hobos;

import by.Lenson423.docks_and_hobos.Exceptions.ShipGoDownException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class Tunel {
    final Queue<Ship> shipQueue = new ArrayDeque<>();
    final int maxShips;

    public Tunel(int maxShips) {
        this.maxShips = maxShips;
    }

    public synchronized void tryAddShipToTunel(@NotNull Ship ship){
        if (shipQueue.size() == maxShips){
            throw new ShipGoDownException();
        }
        shipQueue.add(ship);
        notify();
    }

    public synchronized Ship getPassedShip() throws InterruptedException {
        if (shipQueue.isEmpty()){
            wait();
        }
        return shipQueue.remove();
    }

}
