package by.MikhailShurov.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;

public class Tunnel implements Runnable{
    private static final Logger logger = LoggerUtil.getLogger();
    Queue<Ship> tunnel_;
    boolean someDocksWereBlocked = false;
    int maxShips_;
    public Tunnel (int maxShips) {
        tunnel_ = new ArrayDeque<>();
        maxShips_ = maxShips;
    }

    public void addShip(Ship ship) {
        if (tunnel_.size() < maxShips_) {
            tunnel_.add(ship);
            logger.info("Ship added to tunnel. Tunnel size = " + tunnel_.size() + "\n");
            System.out.println("TUNNEL: Ship added, tunnel size: " + tunnel_.size());
        } else  {
            logger.info("Ship successfully sank\n");
            System.out.println("TUNNEL: Ship successfully sank");
        }
    }

    public synchronized Ship getShip() throws InterruptedException {
        if (tunnel_.isEmpty()) {
            return null;
        }
        Ship ship = tunnel_.remove();
        logger.info("Ship was moved to dock. Tunnel size = " + tunnel_.size() + "\n");
        System.out.println("TUNNEL: Ship removed, tunnel size:" + tunnel_.size());
        return ship;
    }

    @Override
    public void run() {
        while (true) {
            if (!tunnel_.isEmpty() && someDocksWereBlocked) {
                someDocksWereBlocked = false;
                tunnel_.notifyAll();
            }
        }
    }
}
