package by.AlexHanimar.docs_and_hobos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class DocksAndHobos {
    private static final int REFRESH_INTERVAL = 10;

    public static void main(String[] args) throws IOException, ParseException {
        String configPath = args[0];
        var path = Paths.get(configPath);
        String logPath = path.getParent() + "\\logs";

        JSONParser parser = new JSONParser();
        var object = (JSONObject) parser.parse(new FileReader(configPath));
        int generatingTime = Integer.parseInt(object.get("generating_time").toString());
        int shipCapacityMin = Integer.parseInt(object.get("ship_capacity_min").toString());
        int shipCapacityMax = Integer.parseInt(object.get("ship_capacity_max").toString());
        var cargoTypes = new ArrayList<String>();
        var cargo_types = (JSONArray) object.get("cargo_types");
        for (var type : cargo_types) {
            cargoTypes.add(type.toString());
        }
        int maxShips = Integer.parseInt(object.get("max_ships").toString());
        int unloadingSpeed = Integer.parseInt(object.get("unloading_speed").toString());
        var dock_capacity = (JSONObject) object.get("dock_capacity");
        var dockCapacity = new HashMap<String, Integer>();
        for (var key : dock_capacity.keySet()) {
            dockCapacity.put(key.toString(), Integer.parseInt(dock_capacity.get(key).toString()));
        }
        int hobos = Integer.parseInt(object.get("hobos").toString());
        int stealingTime = Integer.parseInt(object.get("stealing_time").toString());
        int eatingTime = Integer.parseInt(object.get("eating_time").toString());
        var ingredients_count = (JSONObject) object.get("ingredients_count");
        var ingredientsCount = new HashMap<String, Integer>();
        for (var key : ingredients_count.keySet()) {
            ingredientsCount.put(key.toString(), Integer.parseInt(ingredients_count.get(key).toString()));
        }

        var rng = new Random(42);
        var tunnel = new Tunnel(maxShips);
        var dock = new Dock(dockCapacity);
        var gen = new ShipGenerator(shipCapacityMin, shipCapacityMax, cargoTypes, rng);

        var shipManager = new ShipManager(gen, tunnel, generatingTime);
        var dockManager = new DockManager(dock, tunnel, unloadingSpeed);

        var th1 = new Thread(shipManager);
        var th2 = new Thread(dockManager);
        th1.start();
        th2.start();

        var hobosManager = new HoboGroupManager(hobos, dock, eatingTime, stealingTime, ingredientsCount, rng);

        var th3 = new Thread(hobosManager);
        th3.start();

        var loggers = new ArrayList<Logger>();
        loggers.add(ShipManager.getLogger());
        loggers.add(DockManager.getLogger());
        loggers.add(Hobo.getLogger());
        loggers.add(HoboGroupManager.getLogger());
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        var loggerManager = new LoggerManager(loggers, REFRESH_INTERVAL, logPath);

        var th4 = new Thread(loggerManager);
        th4.start();
    }
}
