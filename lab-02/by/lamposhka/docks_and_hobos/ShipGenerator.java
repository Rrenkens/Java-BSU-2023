package by.lamposhka.docks_and_hobos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable{
    private Bay bay;
    private static final Logger LOGGER = Logger.getLogger("logger");
    private boolean running = false;
    ShipGenerator(Bay bay) { //
        this.bay = bay;
        running = true;
        LOGGER.log(Level.INFO, "ship generator created");
    }
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bay.enterBay(new Ship(10, "", 7));
            LOGGER.log(Level.INFO, "new ship has been created");
        }
    }
    public void stop() {
        running = false;
        LOGGER.log(Level.INFO, "ship generator has stopped");
    }
}
