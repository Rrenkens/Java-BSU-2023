package by.lamposhka.docks_and_hobos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable{
    private static final Logger LOGGER = Logger.getLogger("logger");
    ShipGenerator() { //
        LOGGER.log(Level.INFO, "ship generator created");
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Controller.getInstance().getTunnel().add(new Ship(10, "rock", 7));
            LOGGER.log(Level.INFO, "new ship has been created");
        }
    }
}
