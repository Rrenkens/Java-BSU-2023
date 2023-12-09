package by.katierevinska.docks_and_hobos;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Process {
    Reader reader;
    ShipGenerator shipGenerator;
    List<Ship> ships;
    Tunnel tunnel = new Tunnel();
    List<Dock> docks = new ArrayList<>();
    GroupOfHobos hobos;

    void createShipGenerator() {
        shipGenerator = new ShipGenerator(this);
    }

    void createHobos() {
        hobos = new GroupOfHobos(this);
    }

    void createDock() {
        for (int i = 0; i < hobos.getHobosCount(); ++i) {
            docks.add(new Dock(this));
        }
    }

    void createObjects(String fileName) throws IOException, ParseException, JSONException {
        reader = new Reader(fileName);
        JSONObject configParam = reader.getJsonObject();

        JSONArray jsonCargoTypes = (JSONArray) configParam.get("cargo_types");
        List<String> cargoTypes = new ArrayList<>();
        int sizeOfTypes = jsonCargoTypes.size();
        for (int i = 0; i < sizeOfTypes; i++) {
            cargoTypes.add((String)jsonCargoTypes.get(i));
        }
        Map<String, Long> ingredientsCount = new HashMap<>();
        JSONArray jsonIngredientsCount = (JSONArray) configParam.get("ingredients_count");
        int sizeOfIngredients = jsonIngredientsCount.size();
        for (int i = 0; i < sizeOfIngredients; i++) {
            ingredientsCount.put(cargoTypes.get(i), (Long)jsonIngredientsCount.get(i));
        }
        createShipGenerator();
        shipGenerator.setGeneratingTime((Long) configParam.get("generating_time"))
                .setShipCapacityMin((Long) configParam.get("ship_capacity_min"))
                .setShipCapacityMax((Long) configParam.get("ship_capacity_max"))
                .setCargoType(cargoTypes);
        createHobos();
        hobos.setHobosStealingTime((Long) configParam.get("stealing_time"))
                .setHobosEatingTime((Long) configParam.get("eating_time"))
                .setHobosCount(Integer.parseInt(configParam.get("hobos_count").toString()))
                .setCargoTypes(cargoTypes)
                .setIngredientsCount(ingredientsCount);
        hobos.generateHobos();
        tunnel.setMaxShips((Long) configParam.get("max_ships"));

        createDock();
        JSONArray jsonArrayOfDocks = (JSONArray) configParam.get("docks");
        int countOfDocks = jsonArrayOfDocks.size();
        for (int i = 0; i < countOfDocks; i++) {
            JSONObject dock = (JSONObject) jsonArrayOfDocks.get(i);
            docks.get(i).setUnloadingSpeed((Long) dock.get("unloading_speed"));
            Map<String, Integer> capacityForIngredients = new HashMap<>();
            for (int j = 0; j < sizeOfIngredients; j++) {
                capacityForIngredients.put(cargoTypes.get(j), (Integer) dock.get(cargoTypes.get(j)));
            }
            docks.get(i).setDockCapacity(capacityForIngredients);
        }
    }

    void startProcess() {
        List<Thread> threads = new ArrayList<>();

        for (var dock : docks) {
            Thread thread = new Thread(dock);
            threads.add(thread);
        }

        Thread hobosGroupThread = new Thread(hobos);
        threads.add(hobosGroupThread);

        Thread shipGeneratorThread = new Thread(shipGenerator);
        threads.add(shipGeneratorThread);

        for (var thread : threads) {
            thread.start();
        }
    }

}
