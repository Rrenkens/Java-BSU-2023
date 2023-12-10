package by.Bvr_Julia.docks_and_hobos;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static Long time = (long) 60;

    public static void main(String[] args) throws Exception {
        String str = "config.json";
        //str = args[0];
        Object o = new JSONParser().parse(new FileReader(str));

        JSONObject j = (JSONObject) o;

        Long generating_time = (Long) j.get("generating_time");
        Long ship_capacity_min = (Long) j.get("ship_capacity_min");
        Long ship_capacity_max = (Long) j.get("ship_capacity_max");
        Long max_ships = (Long) j.get("max_ships");
        Long unloading_speed = (Long) j.get("unloading_speed");
        Long hobos = (Long) j.get("hobos");
        Long stealing_time = (Long) j.get("stealing_time");
        Long eating_time = (Long) j.get("eating_time");
        List<String> cargo_types = (List<String>) j.get("cargo_types");
        Map<String, Long> dock_capacity = (Map<String, Long>) j.get("dock_capacity");
        Map<String, Long> ingridients_count = (Map<String, Long>) j.get("ingridients_count");

        System.out.println("Parsed data \n");


        List<Ship> ships = new CopyOnWriteArrayList<>();
        ShipGenerator shipGenerator = new ShipGenerator(ships, generating_time, ship_capacity_min, ship_capacity_max, cargo_types);
        Tonnel tonnel = new Tonnel(ships, max_ships);
        Dock dock = new Dock(ships, unloading_speed, dock_capacity);
        List<Hobo> hobo = new ArrayList<>();
        Map<String, Long> ingridients = new HashMap<>();
        for (String cargo : ingridients_count.keySet()) {
            ingridients.put(cargo, (long) 0);
        }
        for (int i = 0; i < hobos; i++) {
            hobo.add(new Hobo(dock, i, ingridients, cargo_types, stealing_time));
        }
        HobosBase hobosBase = new HobosBase(ingridients, hobo, ingridients_count, stealing_time, eating_time);

        shipGenerator.getThread().start();
        tonnel.getThread().start();
        dock.getThread().start();
        hobosBase.getThread().start();
        for (int i = 2; i < hobo.size(); i++) {
            hobo.get(i).getThread().start();
        }

        try {
            shipGenerator.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            tonnel.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            dock.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            hobosBase.getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 2; i < hobo.size(); i++) {
            try {
                hobo.get(i).getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void print() {
        JSONObject j = new JSONObject();

        j.put("generating_time", 10);

        j.put("ship_capacity_min", 10);

        j.put("ship_capacity_max", 30);

        j.put("max_ships", 20);

        j.put("unloading_speed", 10);

        j.put("hobos", 10);

        j.put("stealing_time", 5);

        j.put("eating_time", 30);

        j.put("cargo_types", List.of("Ham", "Bread", "Butter"));

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("Ham", 100);
        map1.put("Bread", 200);
        map1.put("Butter", 150);
        j.put("dock_capacity", map1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("Ham", 3);
        map2.put("Bread", 2);
        map2.put("Butter", 5);
        j.put("ingridients_count", map2);
        try (PrintWriter out = new PrintWriter(new FileWriter("config.json"))) {
            out.write(j.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(j);
    }
}