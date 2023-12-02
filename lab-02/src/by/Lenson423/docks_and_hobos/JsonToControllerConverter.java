package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonToControllerConverter {
    static Controller readJsonAndGetController(@NotNull String filePath) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject generator = (JSONObject) jsonObject.get("generator");
            int generatingTime = (int) generator.get("generating_time");
            JSONObject ship = (JSONObject) jsonObject.get("ship");
            int shipCapacityMin = (int) ship.get("ship_capacity_min");
            int shipCapacityMax = (int) ship.get("ship_capacity_max");
            JSONArray cargoTypes = (JSONArray) ship.get("cargo_types");
            ArrayList<String> cargoList = new ArrayList<>(cargoTypes.size());
            for (var elem: cargoTypes){
                cargoList.add((String) elem);
            }
            ShipGenerator shipGenerator = new ShipGenerator(generatingTime, shipCapacityMin,
                    shipCapacityMax, cargoList);

            JSONObject tunnel = (JSONObject) jsonObject.get("tunnel");
            int maxShips = (int) tunnel.get("max_ships");
            Tunel tunel = new Tunel(maxShips);

            ArrayList<Dock> docks = new ArrayList<>();
            JSONArray docksArray = (JSONArray) jsonObject.get("docks");
            for (Object o : docksArray) {
                JSONObject dock = (JSONObject) o;
                int unloadingSpeed = (int) dock.get("unloading_speed");
                JSONObject dockCapacity = (JSONObject) dock.get("dock_capacity");
                int k = cargoTypes.size();
                int[] dockCapacityInt = new int[k];
                for (int j = 0; j < k; j++) {
                    dockCapacityInt[j] = (int) dockCapacity.get((String) cargoTypes.get(j));
                }
                Dock dockToAdd = new Dock(unloadingSpeed, dockCapacityInt);
                docks.add(dockToAdd);
            }

            JSONObject hobos = (JSONObject) jsonObject.get("hobos");
            JSONArray ingredientsCount = (JSONArray) hobos.get("ingredients_count");
            int k = ingredientsCount.size();
            int[] ingredientsCountInt = new int[k];
            for(int i = 0; i < k; ++i){
                ingredientsCountInt[i] = (int) ingredientsCount.get(i);
            }
            int eatingTime = (int) hobos.get("eating_time");
            JSONArray hobosStealingTimeList = (JSONArray) hobos.get("hobos_stealing_time_list");
            k = ingredientsCount.size();
            int[] hobosStealingTimeListInt = new int[k];
            for(int i = 0; i < k; ++i){
                hobosStealingTimeListInt[i] = (int) hobosStealingTimeList.get(i);
            }
            HobosGroup hobosGroup = new HobosGroup(ingredientsCountInt, eatingTime, hobosStealingTimeListInt);
            return Controller.cetControllerInstance(cargoList, docks, tunel, shipGenerator, hobosGroup);
        } catch (Exception e) {
            throw new IOException("Cannot parse json file");
        }
    }
}
