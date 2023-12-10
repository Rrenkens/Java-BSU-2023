package by.AlexHanimar.docs_and_hobos;

import by.AlexHanimar.docs_and_hobos.Dock;
import by.AlexHanimar.docs_and_hobos.Tunnel;

import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.Math.min;
import static java.lang.Thread.sleep;
import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.INFO;

public class DockManager implements Runnable {

    private final Dock dock;
    private final Tunnel tunnel;
    private final int unloadingSpeed;
    private static final Logger logger = Logger.getLogger(DockManager.class.getName());

    public DockManager(Dock dock, Tunnel tunnel, int unloadingSpeed) {
        this.dock = dock;
        this.tunnel = tunnel;
        this.unloadingSpeed = unloadingSpeed;
    }

    @Override
    public void run() {
        logger.log(INFO, "DockManager started");
        while (true) {
            Optional<Ship> shipOptional;
            synchronized (tunnel) {
                shipOptional = tunnel.getShip();
            }
            if (shipOptional.isEmpty()) {
                continue;
            }
            var ship = shipOptional.get();
            logger.log(CONFIG, "Started processing ship " + ship.getName());
            var cargo = ship.getProduct();
            String cargoType = cargo.getName();
            while (cargo.getCount() > 0) {
                synchronized (dock) {
                    int delta = min(dock.getCapacity().get(cargoType) - dock.getCount().get(cargoType), unloadingSpeed);
                    delta = min(delta, cargo.getCount());
                    int dockCount = dock.getCount().get(cargoType);
                    dock.getCount().replace(cargoType, dockCount + delta);
                    cargo.SubCount(delta);
                }
                try {
                    sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.log(CONFIG, "Finished processing ship " + ship.getName());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
