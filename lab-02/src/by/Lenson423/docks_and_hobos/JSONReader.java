package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    static Controller readJsonAndGetController(@NotNull String filePath) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("your_json_file_path.json"));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject generator = (JSONObject) jsonObject.get("generator");
            int generatingTime = (int) generator.get("generating_time");

            JSONObject ship = (JSONObject) jsonObject.get("ship");
            int shipCapacityMin = (int) ship.get("ship_capacity_min");
            int shipCapacityMax = (int) ship.get("ship_capacity_max");
            JSONArray cargoTypes = (JSONArray) ship.get("cargo_types");

            JSONObject tunnel = (JSONObject) jsonObject.get("tunnel");
            int maxShips = (int) tunnel.get("max_ships");

            JSONObject docks = (JSONObject) jsonObject.get("docks");
            int unloadingSpeed = (int) docks.get("unloading_speed");
            int dockCapacity = (int) docks.get("dock_capacity");

            JSONObject hobos = (JSONObject) jsonObject.get("hobos");
            int hobosCount = (int) hobos.get("hobos_count");
            JSONArray ingredientsCount = (JSONArray) hobos.get("ingredients_count");
            int stealingTime = (int) hobos.get("stealing_time");
            int eatingTime = (int) hobos.get("eating_time");
            JSONArray hobosStealingTimeList = (JSONArray) hobos.get("hobos_stealing_time_list");
        } catch (Exception e) {
            throw new IOException("Can'not parse json file");
        }
        return null; //ToDo
    }
}
