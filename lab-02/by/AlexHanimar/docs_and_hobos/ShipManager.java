package by.AlexHanimar.docs_and_hobos;

import by.AlexHanimar.docs_and_hobos.ShipGenerator;
import by.AlexHanimar.docs_and_hobos.Tunnel;
import by.AlexHanimar.docs_and_hobos.Dock;

import java.util.logging.Logger;

import java.util.Map;
import static java.lang.Thread.sleep;
import static java.util.logging.Level.*;

public class ShipManager implements Runnable {
    private final Tunnel tunnel;
    private final ShipGenerator shipGenerator;
    private final int generatingTime;
    private final static Logger logger = Logger.getLogger(ShipManager.class.getName());

    public ShipManager(ShipGenerator shipGenerator, Tunnel tunnel, int generatingTime) {
        this.tunnel = tunnel;
        this.shipGenerator = shipGenerator;
        this.generatingTime = generatingTime;
    }

    @Override
    public void run() {
        logger.log(INFO, "ShipManager started");
        while (true) {
            var ship = shipGenerator.GenerateShip();
            boolean added = false;
            synchronized (tunnel) {
                added = tunnel.AddShip(ship);
            }
            if (!added) {
                logger.log(CONFIG, "Ship " + ship.getName() + " has drowned");
            } else {
                logger.log(CONFIG, "Ship " + ship.getName() + " has entered tunnel");
            }
            try {
                sleep(generatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
