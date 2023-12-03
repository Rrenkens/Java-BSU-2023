package by.Lenson423.docks_and_hobos.utilities;

import by.Lenson423.docks_and_hobos.main_actors.Dock;
import by.Lenson423.docks_and_hobos.main_actors.HobosGroup;
import by.Lenson423.docks_and_hobos.main_actors.ShipGenerator;
import by.Lenson423.docks_and_hobos.main_actors.Tunel;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonToControllerConverter {
    public static Controller readJsonAndGetController(@NotNull String filePath) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            String newPath = convertPath(filePath);
            File directory = new File(newPath, "log");

            if (!directory.exists()) {
                directory.mkdir();
            }

            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject generator = (JSONObject) jsonObject.get("generator");
            int generatingTime = Integer.parseInt(generator.get("generating_time").toString());
            JSONObject ship = (JSONObject) jsonObject.get("ship");
            int shipCapacityMin = Integer.parseInt(ship.get("ship_capacity_min").toString());
            int shipCapacityMax = Integer.parseInt(ship.get("ship_capacity_max").toString());
            JSONArray cargoTypes = (JSONArray) ship.get("cargo_types");
            ArrayList<String> cargoList = new ArrayList<>(cargoTypes.size());
            for (var elem : cargoTypes) {
                cargoList.add(elem.toString());
            }
            ShipGenerator shipGenerator = new ShipGenerator(generatingTime, shipCapacityMin,
                    shipCapacityMax, cargoList);

            JSONObject tunnel = (JSONObject) jsonObject.get("tunnel");
            int maxShips = Integer.parseInt(tunnel.get("max_ships").toString());
            Tunel tunel = new Tunel(maxShips);

            ArrayList<Dock> docks = new ArrayList<>();
            JSONArray docksArray = (JSONArray) jsonObject.get("docks");
            for (Object o : docksArray) {
                JSONObject dock = (JSONObject) o;
                int unloadingSpeed = Integer.parseInt(dock.get("unloading_speed").toString());
                JSONObject dockCapacity = (JSONObject) dock.get("dock_capacity");
                int k = cargoTypes.size();
                int[] dockCapacityInt = new int[k];
                for (int j = 0; j < k; j++) {
                    dockCapacityInt[j] = Integer.parseInt(dockCapacity.get(cargoTypes.get(j)).toString());
                }
                Dock dockToAdd = new Dock(unloadingSpeed, dockCapacityInt);
                docks.add(dockToAdd);
            }

            JSONObject hobos = (JSONObject) jsonObject.get("hobos");
            JSONArray ingredientsCount = (JSONArray) hobos.get("ingredients_count");
            int k = ingredientsCount.size();
            int[] ingredientsCountInt = new int[k];
            for (int i = 0; i < k; ++i) {
                ingredientsCountInt[i] = Integer.parseInt(((JSONObject) ingredientsCount.get(i)).
                        get(cargoList.get(i)).toString());
            }
            int eatingTime = Integer.parseInt(hobos.get("eating_time").toString());
            JSONArray hobosStealingTimeList = (JSONArray) hobos.get("hobos_stealing_time_list");
            k = ingredientsCount.size();
            int[] hobosStealingTimeListInt = new int[k];
            for (int i = 0; i < k; ++i) {
                hobosStealingTimeListInt[i] = Integer.parseInt(hobosStealingTimeList.get(i).toString());
            }
            HobosGroup hobosGroup = new HobosGroup(ingredientsCountInt, eatingTime, hobosStealingTimeListInt);
            return Controller.cetControllerInstance(newPath, cargoList, docks, tunel, shipGenerator, hobosGroup);
        } catch (Exception e) {
            throw new IOException("Cannot parse json file");
        }
    }

    public static String convertPath(String inputPath) {
        Path path = Paths.get(inputPath);
        Path rootPath = path.getRoot().resolve("Java-BSU-2023\\lab-02\\src\\by\\Lenson423\\docks_and_hobos");
        return rootPath.toString();
    }
}
