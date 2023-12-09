package by.BelArtem.docks_and_hobos;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Program {
    private int generating_time;
    private int ship_capacity_min;
    private int ship_capacity_max;
    private ArrayList<String> cargo_types;
    private int max_ships;
    private int unloading_speed;
    private int dock_capacity;
    private int hobos;

    private ArrayList<Integer> ingredients_count;
    private int stealing_time;
    private int eating_time;

    private int docks_number;

    public Program() {

    }

    /*private void writeToJsonFile() {
        JsonObject jsonObject = new JsonObject();
        int[] dj = {1,2};
        jsonObject.addProperty("generating_time", 1);
        jsonObject.addProperty("generating_time", 1);
        jsonObject.add("Arr", JsonElement);



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonObject);

        try(FileWriter fileWriter = new FileWriter("input.json");
            JsonWriter jsonWriter = new JsonWriter(fileWriter)
        ) {

            fileWriter.write(json);

            jsonWriter.beginObject();
            jsonWriter.name("ddd").value(10);
            jsonWriter.name("ddd").value(10);
            jsonWriter.endObject();

            System.out.println("Write JSON file successfully at D:\\Data\\test1.json");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }*/

    private void readFromJsonFile() {
        try {
            String filePath = "src\\by\\BelArtem\\docks_and_hobos\\config.json";
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();
            System.out.println("Hi");

            generating_time = jsonObject.get("generating_time").getAsInt();
            ship_capacity_min = jsonObject.get("ship_capacity_min").getAsInt();
            ship_capacity_max = jsonObject.get("ship_capacity_max").getAsInt();

            String[] cargoTypes = new Gson().fromJson(jsonObject.get("cargo_types"), String[].class);
            cargo_types = new ArrayList<>(List.of(cargoTypes));

            max_ships = jsonObject.get("max_ships").getAsInt();

            unloading_speed = jsonObject.get("unloading_speed").getAsInt();
            dock_capacity = jsonObject.get("dock_capacity").getAsInt();
            //dockCapacity = new Gson().fromJson(jsonObject.get("dockCapacity"), int[].class);
            hobos = jsonObject.get("hobos").getAsInt();

            Integer[] ingredientsCount = new Gson().fromJson(jsonObject.get("ingredients_count"), Integer[].class);
            ingredients_count = new ArrayList<>(List.of(ingredientsCount));

            stealing_time = jsonObject.get("stealing_time").getAsInt();
            eating_time = jsonObject.get("eating_time").getAsInt();

            docks_number = jsonObject.get("docks_number").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (generating_time < 0 || ship_capacity_min < 0 ||
                ship_capacity_max < ship_capacity_min || cargo_types.isEmpty() ||
                max_ships < 0 || unloading_speed < 0 || dock_capacity < 0 ||
                hobos < 3 || ingredients_count.isEmpty() || stealing_time < 0 ||
                eating_time < 0 || docks_number < 0) {
            throw new IllegalArgumentException("Some input data is invalid");
        }

    }

    public void start() {
        this.readFromJsonFile();

        ShipGenerator generator = new ShipGenerator(generating_time, ship_capacity_min,
                ship_capacity_max, cargo_types);

        Thread generateThread = new Thread(generator, "Generator");

        Tunnel tunnel = new Tunnel(max_ships);
        TunnelManager tunnelManager = new TunnelManager(tunnel, generator);

        Thread tunnelManagerThread = new Thread(tunnelManager);


        List<Dock> lst = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < docks_number; ++i) {
            lst.add(new Dock(unloading_speed, dock_capacity, tunnelManager, cargo_types));
        }
        DocksManager docksManager = new DocksManager(lst);

        Thread dockManagerThread = new Thread(docksManager);



        int size = ingredients_count.size();
        AtomicIntegerArray ingredients = new AtomicIntegerArray(size);
        for (int i = 0; i < size; ++i) {
            ingredients.set(i, this.ingredients_count.get(i));
            System.out.println(ingredients.get(i));
            System.out.println("Bye");
        }

        ArrayList<Hobo> hobosList = new ArrayList<>();
        for (int i = 0; i < this.hobos; ++i) {
            hobosList.add(new Hobo(lst, ingredients, stealing_time));
        }

        HobosManager hobosManager = new HobosManager(hobosList, eating_time, ingredients);

        Thread hobosManagerThread = new Thread(hobosManager);

        generateThread.start();
        tunnelManagerThread.start();
        dockManagerThread.start();
        hobosManagerThread.start();

    }

}




/*
public class ConfigReader {
    private int generatingTime;
    private int shipCapacityMin;
    private int shipCapacityMax;
    private String[] cargoTypes;
    private int maxShips;
    private int docks;
    private int unloadingSpeed;
    private int[] dockCapacity;
    private int hobos;
    private int[] ingredientsCount;
    private int stealingTime;
    private int eatingTime;



    public ConfigReader(String filePath) {
        try {
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

            generatingTime = jsonObject.get("generatingTime").getAsInt();
            shipCapacityMin = jsonObject.get("shipCapacityMin").getAsInt();
            shipCapacityMax = jsonObject.get("shipCapacityMax").getAsInt();
            cargoTypes = new Gson().fromJson(jsonObject.get("cargoTypes"), String[].class);
            maxShips = jsonObject.get("maxShips").getAsInt();
            docks = jsonObject.get("docks").getAsInt();
            unloadingSpeed = jsonObject.get("unloadingSpeed").getAsInt();
            dockCapacity = new Gson().fromJson(jsonObject.get("dockCapacity"), int[].class);
            hobos = jsonObject.get("hobos").getAsInt();
            ingredientsCount = new Gson().fromJson(jsonObject.get("ingredientsCount"), int[].class);
            stealingTime = jsonObject.get("stealingTime").getAsInt();
            eatingTime = jsonObject.get("eatingTime").getAsInt();
        } catch (IOException e) {
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

    public int getDocks() {
        return docks;
    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public int[] getDockCapacity() {
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
}*/
