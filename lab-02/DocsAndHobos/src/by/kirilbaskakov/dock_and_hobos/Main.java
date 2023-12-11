package by.kirilbaskakov.dock_and_hobos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            JsonObject config = JsonParser.parseReader(new FileReader("config.json")).getAsJsonObject();
            int generatingTime = config.get("generating_time").getAsInt();
            int shipCapacityMin = config.get("ship_capacity_min").getAsInt();
            int shipCapacityMax = config.get("ship_capacity_max").getAsInt();
            int maxShips = config.get("max_ships").getAsInt();
            String[] cargoTypes = new Gson().fromJson(config.get("cargo_types"), String[].class);
            int unloadingSpeed = config.get("unloading_speed").getAsInt();
            int dockCapacity = config.get("dock_capacity").getAsInt();
            int hobos = config.get("hobos").getAsInt();
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> ingredients_count = gson.fromJson(config.getAsJsonObject("ingredients_count"), type);
            int eatingTime = config.get("eating_time").getAsInt();
            int stealingTime = config.get("stealing_time").getAsInt();

            Tunnel tunnel = new Tunnel(maxShips);
            ShipGenerator shipGenerator = new ShipGenerator(generatingTime, shipCapacityMin, shipCapacityMax, cargoTypes, tunnel);
            Dock dock = new Dock(unloadingSpeed,dockCapacity, tunnel);
            Hobos hobosGroup = new Hobos(hobos, ingredients_count, eatingTime, stealingTime, dock);

            dock.startUnloading();
            shipGenerator.startGenerating();
            hobosGroup.startSteal();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}