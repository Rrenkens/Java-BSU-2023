package by.lamposhka.docks_and_hobos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.TableHeaderUI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static Controller controller;
    private static int ship_capacity_max;

    public static void initializeController(String path) throws Exception {
        Object object = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) object;

        int generating_time = Integer.parseInt(jo.get("generating_time").toString());
        int ship_capacity_min = Integer.parseInt(jo.get("ship_capacity_min").toString());
        int ship_capacity_max = Integer.parseInt(jo.get("ship_capacity_max").toString());

        ArrayList<String> cargo_types = new ArrayList<>();
        JSONArray tempTypes = (JSONArray) jo.get("cargo_types");
        for (var i : tempTypes) {
            cargo_types.add(i.toString());
        }
        int max_ships = Integer.parseInt(jo.get("max_ships").toString());
        int unloading_speed = Integer.parseInt(jo.get("unloading_speed").toString());
        int dock_count = Integer.parseInt(jo.get("dock_count").toString());


        HashMap<String, Integer> dock_capacity = new HashMap<>();
        JSONObject tempCapacity = (JSONObject) jo.get("dock_capacity");
        for (var i : tempCapacity.keySet()) {
            dock_capacity.put(i.toString(), Integer.parseInt(tempCapacity.get(i).toString()));
        }
        int hobos_count =  Integer.parseInt(jo.get("hobos").toString());

        HashMap<String, Integer> ingredients_count = new HashMap<>();
        JSONObject tempIngredients = (JSONObject) jo.get("ingredients_count");
        for (var i : tempIngredients.keySet()) {
            ingredients_count.put(i.toString(), Integer.parseInt(tempIngredients.get(i).toString()));
        }

        int stealing_time = Integer.parseInt(jo.get("stealing_time").toString());
        int eating_time = Integer.parseInt(jo.get("eating_time").toString());

        ArrayList<Dock> docks = new ArrayList<>();
        for (int i = 0; i < dock_count; ++i) {
            docks.add(new Dock(unloading_speed, dock_capacity));
        }
        Hobos hobos = new Hobos(hobos_count, ingredients_count, stealing_time, eating_time);
        controller = new Controller(new Tunnel(5),
                new ShipGenerator(ship_capacity_min, ship_capacity_max, cargo_types, generating_time),
                docks, hobos
        );
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong arguments provided.");
        }
        String path = args[0];
        initializeController(path);
        controller.start();
    }
}
