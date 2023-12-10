package by.busskov.docks_and_hobos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;


public class Bay {
    public static void main(String[] args) throws InterruptedException {
        BayLogger logger = new BayLogger(Level.INFO, Level.ALL, 10000);

        Tunnel tunnel = new Tunnel(5, logger);

        ArrayList<String> cargoTypes = new ArrayList<>(Arrays.asList("Guns", "Drugs", "Iphones", "Bananas"));
        Ship.Generator generator = new Ship.Generator(
                10,
                100,
                1000,
                cargoTypes,
                tunnel,
                logger);

        HashMap<String, Integer> necessaryIngredients = new HashMap<>();
        necessaryIngredients.put("Bananas", 40);
        necessaryIngredients.put("Iphones", 10);

        Dock dock = new Dock(
                10,
                100,
                cargoTypes,
                tunnel,
                logger,
                10,
                20000,
                1000,
                necessaryIngredients);

        Thread loggerThread = new Thread(logger, "Logger");
        Thread dockThread = new Thread(dock, "Dock");
        Thread generatorThread = new Thread(generator, "Ship generator");
        loggerThread.start();
        generatorThread.start();
        dockThread.start();

        loggerThread.join();
        generatorThread.join();
        dockThread.join();
    }
}
