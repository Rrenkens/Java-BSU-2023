package by.Lenson423.docks_and_hobos.main_actors;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class Tunel {
    final Queue<Ship> shipQueue = new ArrayDeque<>();
    final int maxShips;
    private static final Logger logger = Logger.getLogger(Tunel.class.getName());

    public Tunel(int maxShips) {
        this.maxShips = maxShips;
    }

    public synchronized void tryAddShipToTunel(@NotNull Ship ship) {
        String text = "Ship with type " + ship.getShipType()
                + " and capacity " + ship.getShipCapacity();
        if (shipQueue.size() == maxShips) {
            logger.log(INFO, text + " go down");
        }
        shipQueue.add(ship);
        logger.log(INFO, text + " was added to tunel");
        notify();
    }

    public synchronized Ship getPassedShip() throws InterruptedException {
        if (shipQueue.isEmpty()) {
            wait();
        }
        return shipQueue.remove();
    }

    public static Logger getLogger(){
        return logger;
    }
}
