package by.Dzenia.docks_and_hobos.Controller;
import by.Dzenia.docks_and_hobos.Persons.Cargo;
import by.Dzenia.docks_and_hobos.Persons.Tunnel;
import by.Dzenia.docks_and_hobos.RunnableObjects.Dock;
import by.Dzenia.docks_and_hobos.RunnableObjects.Hobos;
import by.Dzenia.docks_and_hobos.RunnableObjects.ShipGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Model {
    private final Tunnel tunnel;
    private final Hobos hobos;
    private final ShipGenerator shipGenerator;
    private final ArrayList<Cargo> cargos;
    private final ArrayList<Dock> docks;

    public Model(Tunnel tunnel, Hobos hobos, ShipGenerator shipGenerator, ArrayList<Cargo> cargos, ArrayList<Dock> docks) {
        this.tunnel = tunnel;
        this.hobos = hobos;
        this.shipGenerator = shipGenerator;
        this.cargos = cargos;
        this.docks = docks;
    }

    public Model(String pathToJson) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJson)));
        JSONObject jsonObject = new JSONObject(jsonContent);
        JSONObject generatorJson = jsonObject.getJSONObject("generator");
        int generatingTime = Integer.parseInt(generatorJson.get("generating_time").toString());
        JSONObject ship = jsonObject.getJSONObject("ship");
        int shipCapacityMin = Integer.parseInt(ship.get("ship_capacity_min").toString());
        int shipCapacityMax = Integer.parseInt(ship.get("ship_capacity_max").toString());
        this.shipGenerator = new ShipGenerator(generatingTime, shipCapacityMin, shipCapacityMax, this);
        JSONArray cargoTypes = (JSONArray) ship.get("cargo_types");
        cargos = new ArrayList<>();
        for (Object cargoType : cargoTypes) {
            cargos.add(new Cargo(cargoType.toString()));
        }
        JSONObject tunnel = jsonObject.getJSONObject("tunnel");
        int maxShips = Integer.parseInt(tunnel.get("max_ships").toString());
        this.tunnel = new Tunnel(maxShips);
        this.docks = new ArrayList<>();
        JSONArray docksArray = jsonObject.getJSONArray("docks");
        for (Object dockObject : docksArray) {
            JSONObject dock = (JSONObject) dockObject;
            int speed = Integer.parseInt(dock.get("unloading_speed").toString());
            JSONObject dockCapacity = dock.getJSONObject("dock_capacity");
            HashMap<String, Integer> capacities = new HashMap<>();
            Map<String, Object> mp = dockCapacity.toMap();
            Set<String> keys = dockCapacity.toMap().keySet();
            for (String cargoType: keys) {
                capacities.put(cargoType, (Integer)mp.get(cargoType));
            }
            docks.add(new Dock(speed, capacities, this));
        }
        JSONObject hobosJson = jsonObject.getJSONObject("hobos");
        this.hobos = new Hobos(hobosJson.getJSONObject("ingredients_count")
                .toMap()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (Integer)entry.getValue()
                )),
                hobosJson.getJSONObject("hobos_stealing_time")
                        .toMap()
                        .entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> (Integer)entry.getValue()
                        )),
                (int)hobosJson.get("eating_time"), this);
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public Hobos getHobos() {
        return hobos;
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    public ArrayList<Cargo> getCargos() {
        return cargos;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }
}
