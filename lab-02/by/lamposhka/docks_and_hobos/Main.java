package by.lamposhka.docks_and_hobos;

import javax.swing.plaf.TableHeaderUI;
import java.util.HashSet;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Bay bay = new Bay();
        ShipGenerator generator = new ShipGenerator(bay);
        Thread generatorThread = new Thread(generator);
        generatorThread.setDaemon(true);
        generatorThread.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
