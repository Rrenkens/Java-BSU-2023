package by.MikhailShurov.docks_and_hobos;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.*;

public class Main {
    private static final Logger logger = LoggerUtil.getLogger();
    public static void main(String[] args) {


        Tunnel tunnel = new Tunnel(3);

        ShipGenerator shipGenerator = new ShipGenerator(tunnel);
        Thread shipGeneratorThread = new Thread(shipGenerator);

        Integer dockAmount = 4;
        logger.info("Docks amount = " + dockAmount + "\n");
        System.out.println("MAIN: DOCKS AMOUNT: " + dockAmount);
        Bay bay = new Bay(dockAmount, tunnel);
        Thread bayThread = new Thread(bay);

        shipGeneratorThread.start();
        bayThread.start();
    }
}
