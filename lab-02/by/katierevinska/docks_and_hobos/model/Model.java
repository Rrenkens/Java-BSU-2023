package by.katierevinska.docks_and_hobos.model;

import by.katierevinska.docks_and_hobos.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private List<String> cargoTypes;
    private ShipGenerator shipGenerator;
    private Tunnel tunnel;
    private List<Dock> docks;
    private GroupOfHobos hobos;

    public Model() {
        tunnel = new Tunnel();
        docks = new ArrayList<>();
        shipGenerator = new ShipGenerator();
        hobos = new GroupOfHobos();
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public GroupOfHobos getHobos() {
        return hobos;
    }

    public List<Dock> getDocks() {
        return docks;
    }

    public List<String> getCargoTypes() {
        return cargoTypes;
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    private void createShipGenerator(JSONObject configParam) {
        shipGenerator.setGeneratingTime((Long) configParam.get("generating_time"))
                .setShipCapacityMin((Long) configParam.get("ship_capacity_min"))
                .setShipCapacityMax((Long) configParam.get("ship_capacity_max"));
    }
    private void createHobos(JSONObject configParam){
        Map<String, Long> ingredientsCount = new HashMap<>();
        JSONArray jsonIngredientsCount = (JSONArray) configParam.get("ingredients_count");
        int sizeOfIngredients = jsonIngredientsCount.size();
        for (int i = 0; i < sizeOfIngredients; i++) {
            ingredientsCount.put(cargoTypes.get(i), (Long) jsonIngredientsCount.get(i));
        }
        hobos.setNullListIngredients();
        hobos.setHobosStealingTime((Long) configParam.get("stealing_time"))
                .setHobosEatingTime((Long) configParam.get("eating_time"))
                .setHOBOS_COUNT(Integer.parseInt(configParam.get("hobos_count").toString()))
                .setIngredientsCount(ingredientsCount);
        hobos.generateHobos();
    }
private void createDocks(JSONObject configParam){
    JSONArray jsonArrayOfDocks = (JSONArray) configParam.get("docks");
    int countOfDocks = jsonArrayOfDocks.size();
    for (int i = 0; i < countOfDocks; i++) {
        JSONObject dock = (JSONObject) jsonArrayOfDocks.get(i);
        docks.add(new Dock());
        docks.get(i).setUNLOADING_SPEED((Long) dock.get("unloading_speed"));
        Map<String, Integer> capacityForIngredients = new HashMap<>();
        JSONObject caps = (JSONObject) dock.get("dock_capacity");
        for (String cargoType : cargoTypes) {
            capacityForIngredients.put(cargoType, Integer.parseInt(caps.get(cargoType).toString()));
        }
        docks.get(i).setDockCapacity(capacityForIngredients);

    }
}
    public void createObjects(Reader reader) throws IOException, ParseException {
        JSONObject configParam = reader.getJsonObject();

        JSONArray jsonCargoTypes = (JSONArray) configParam.get("cargo_types");
        List<String> cargoTypes = new ArrayList<>();
        int sizeOfTypes = jsonCargoTypes.size();
        for (int i = 0; i < sizeOfTypes; i++) {
            cargoTypes.add((String) jsonCargoTypes.get(i));
        }
        this.cargoTypes = cargoTypes;
        createShipGenerator(configParam);
        createHobos(configParam);
        createDocks(configParam);
        tunnel.setMaxShips((Long) configParam.get("max_ships"));
    }
}
