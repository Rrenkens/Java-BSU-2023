package by.busskov.docks_and_hobos;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import org.json.*;

public class Bay {
    private BayLogger logger;
    private Tunnel tunnel;
    private Ship.Generator generator;
    private Dock dock;

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Program should start with config file path!");
        }
        Bay bay = new Bay();
        bay.readData(args[0]);

        Thread loggerThread = new Thread(bay.getLogger(), "Logger");
        Thread dockThread = new Thread(bay.getDock(), "Dock");
        Thread generatorThread = new Thread(bay.getGenerator(), "Ship generator");
        loggerThread.start();
        generatorThread.start();
        dockThread.start();

        loggerThread.join();
        generatorThread.join();
        dockThread.join();
    }

    public BayLogger getLogger() {
        return logger;
    }

    public Ship.Generator getGenerator() {
        return generator;
    }

    public Dock getDock() {
        return dock;
    }

    public void readData(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);

            int fileUpdateTime = json.getInt("file_update_time");
            int generatingTime = json.getInt("generating_time");
            int minCapacity = json.getInt("ship_capacity_min");
            int maxCapacity = json.getInt("ship_capacity_max");

            JSONArray cargoTypesArray = json.getJSONArray("cargo_types");
            ArrayList<String> cargoTypes = new ArrayList<>(cargoTypesArray.length());
            for (int i = 0; i < cargoTypesArray.length(); ++i) {
                cargoTypes.add(cargoTypesArray.getString(i));
            }

            int maxShips = json.getInt("max_ships");
            int unloadingSpeed = json.getInt("unloading_speed");
            int dockCapacity = json.getInt("dock_capacity");
            int hobos = json.getInt("hobos");
            int stealingTime = json.getInt("stealing_time");
            int eatingTime = json.getInt("stealing_time");
            JSONObject ingredients = json.getJSONObject("ingredients_count");
            HashMap<String, Integer> ingredientsMap = new HashMap<>();
            for (String key : ingredients.keySet()) {
                ingredientsMap.put(key, ingredients.getInt(key));
            }

            logger = new BayLogger(Level.INFO, Level.ALL, fileUpdateTime);
            tunnel = new Tunnel(maxShips, logger);
            generator = new Ship.Generator(
                    minCapacity,
                    maxCapacity,
                    generatingTime,
                    cargoTypes,
                    tunnel,
                    logger
            );
            dock = new Dock(
                    unloadingSpeed,
                    dockCapacity,
                    cargoTypes,
                    tunnel,
                    logger,
                    hobos,
                    eatingTime,
                    stealingTime,
                    ingredientsMap
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
