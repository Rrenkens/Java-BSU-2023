package by.waitingsolong.docks_and_hobos;

import by.waitingsolong.docks_and_hobos.helpers.CargoType;
import by.waitingsolong.docks_and_hobos.helpers.Config;
import by.waitingsolong.docks_and_hobos.helpers.NameDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start of program.");

        String configPath;
        if (args.length == 0) {
            Path path = Paths.get("resources", "config.json");
            configPath = path.toString();
        } else {
            configPath = args[0];
        }

        Config config;

        int max_ships;
        int ship_capacity_min;
        int ship_capacity_max;
        int generating_time;
        int unloading_speed;
        int dock_capacity;
        int hobos;
        int stealing_time;
        int crimes_per_day;
        int cooking_time;
        ArrayList<Integer> ingredients_count;
        ArrayList<String> cargo_types;

        try {
            config = new Config(configPath);

            max_ships = config.get("max_ships");
            ship_capacity_max = config.get("ship_capacity_max");
            ship_capacity_min = config.get("ship_capacity_min");
            generating_time = config.get("generating_time");
            unloading_speed = config.get("unloading_speed");
            dock_capacity = config.get("dock_capacity");
            hobos = config.get("hobos");
            ingredients_count = config.get("ingredients_count");
            stealing_time = config.get("stealing_time");
            crimes_per_day = config.get("crimes_per_day");
            cooking_time = config.get("cooking_time");
            cargo_types = config.get("cargo_types");

        } catch (JSONException | IOException e) {
            logger.error("Error to parse json");
            throw new RuntimeException("Error to parse json");
        }

        CargoType.setTypes(cargo_types);
        NameDistributor.readUniqueNames(Paths.get("resources", "names.txt").toString());

        Tunnel tunnel = new Tunnel(max_ships);
        Dock dock = new Dock(unloading_speed, dock_capacity, tunnel, generating_time);
        ShipGenerator shipGenerator = new ShipGenerator(generating_time, ship_capacity_min, ship_capacity_max, tunnel);
        HoboTent hoboTent = new HoboTent(hobos, stealing_time, crimes_per_day, cooking_time, ingredients_count);

        dock.start();
        shipGenerator.start();
        hoboTent.start();
    }

}
