package by.waitingsolong.docks_and_hobos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Tunnel {
    private static final Logger logger = LogManager.getLogger(Tunnel.class);
    private BlockingQueue<Ship> tunnel;

    public Tunnel(int maxShips) {
        this.tunnel = new LinkedBlockingQueue<>(maxShips);
    }

    public void enter(Ship ship) {
        if (!tunnel.offer(ship)) {
            logger.info("Ship " + ship.getName() + " has not entered the tunnel...");
            Thread.currentThread().interrupt();
        } else {
            logger.info("Ship " + ship.getName() + " entered the tunnel...");
        }
    }

    public boolean call(Dock dock) {
        Ship ship = tunnel.poll();
        if (ship != null) {
            ship.setDock(dock);
            synchronized (ship) {
                logger.info("Ship " + ship.getName() + " is called from the tunnel...");
                ship.notify();
            }
            return true;
        } else {
            return false;
        }
    }
}
