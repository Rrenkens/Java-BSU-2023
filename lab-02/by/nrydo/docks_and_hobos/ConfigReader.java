package by.nrydo.docks_and_hobos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Arrays;

public class ConfigReader {
    private static ConfigReader instance = null;

    private int generatingTime;
    private int shipCapacityMin;
    private int shipCapacityMax;
    private String[] cargoTypes;
    private int maxShips;
    private int unloadingSpeed;
    private int dockCapacity;
    private int hobos;
    private int[] ingredientsCount;
    private int stealingTime;
    private int eatingTime;

    private ConfigReader() {
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public void readConfig(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            generatingTime = Math.toIntExact((Long) jsonObject.get("generating_time"));
            shipCapacityMin = Math.toIntExact((Long) jsonObject.get("ship_capacity_min"));
            shipCapacityMax = Math.toIntExact((Long) jsonObject.get("ship_capacity_max"));

            JSONArray cargoTypesJson = (JSONArray) jsonObject.get("cargo_types");
            cargoTypes = new String[cargoTypesJson.size()];
            for (int i = 0; i < cargoTypesJson.size(); i++) {
                cargoTypes[i] = (String) cargoTypesJson.get(i);
            }

            maxShips = Math.toIntExact((Long) jsonObject.get("max_ships"));
            unloadingSpeed = Math.toIntExact((Long) jsonObject.get("unloading_speed"));
            dockCapacity = Math.toIntExact((Long) jsonObject.get("dock_capacity"));
            hobos = Math.toIntExact((Long) jsonObject.get("hobos"));

            JSONArray ingredientsCountJson = (JSONArray) jsonObject.get("ingredients_count");
            ingredientsCount = new int[cargoTypesJson.size()];
            for (int i = 0; i < ingredientsCountJson.size(); i++) {
                ingredientsCount[i] = ((Long) ingredientsCountJson.get(i)).intValue();
            }

            stealingTime = Math.toIntExact((Long) jsonObject.get("stealing_time"));
            eatingTime = Math.toIntExact((Long) jsonObject.get("eating_time"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGeneratingTime() {
        return generatingTime;
    }

    public int getShipCapacityMin() {
        return shipCapacityMin;
    }

    public int getShipCapacityMax() {
        return shipCapacityMax;
    }

    public String[] getCargoTypes() {
        return cargoTypes;
    }

    public int getMaxShips() {
        return maxShips;
    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public int getDockCapacity() {
        return dockCapacity;
    }

    public int getHobos() {
        return hobos;
    }

    public int[] getIngredientsCount() {
        return ingredientsCount;
    }

    public int getStealingTime() {
        return stealingTime;
    }

    public int getEatingTime() {
        return eatingTime;
    }
}
