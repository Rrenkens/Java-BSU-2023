package by.KseniyaGnezdilova.docks_and_hobos;

import by.KseniyaGnezdilova.docks_and_hobos.threads.DocksThread;
import by.KseniyaGnezdilova.docks_and_hobos.threads.HobosThread;
import by.KseniyaGnezdilova.docks_and_hobos.threads.ShipThread;
import by.KseniyaGnezdilova.docks_and_hobos.threads.StatusThread;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static LinkedList<Ship> tunnel = new LinkedList<>();;
    public static int max_ships;
    public static HashMap<String, Integer> dock = new HashMap<String, Integer>();
    public static HashMap<String, Integer> ingridients = new HashMap<String, Integer>();

    public static Semaphore sem = new Semaphore(1);

    public static void main(String[] args) throws IOException, ParseException, JSONException {

        Object obj = new JSONParser().parse(new FileReader(args[0]));
        JSONObject jsonObject = new JSONObject(obj.toString());

        max_ships =  jsonObject.getInt("max_ships");


        StatusThread statusThread = new StatusThread();

        Vector<String> cargo_types = new Vector<>();
        JSONArray jsonArray = jsonObject.getJSONArray("cargo_types");
        for (int i = 0; i < jsonArray.length(); i++){
            cargo_types.add((String) jsonArray.get(i));
        }


        HashMap<String, Integer> dock_capacity = new HashMap<>( );
        jsonArray = jsonObject.getJSONArray("dock_capacity");
        for (int i = 0; i < jsonArray.length(); i++){
            Object object = jsonArray.get(i);
            JSONObject cargo = new JSONObject(object.toString());
            dock_capacity.put(cargo.getString("key"), cargo.getInt("value"));
        }

        for (var key: dock_capacity.keySet()){
            dock.put(key, 0);
        }

        int generating_time = (int) jsonObject.get("generating_time");
        int ship_capacity_min = (int) jsonObject.get("ship_capacity_min");
        int ship_capacity_max = (int) jsonObject.get("ship_capacity_max");

        ShipThread shipThread = new ShipThread(generating_time , ship_capacity_min, ship_capacity_max, cargo_types);

        int unloading_speed = (int) jsonObject.get("unloading_speed");
        DocksThread docksThread = new DocksThread(unloading_speed, dock_capacity);

        HashMap<String, Integer> ingridients_count = new HashMap<>();
        jsonArray = jsonObject.getJSONArray("ingridients_count");
        for (int i = 0; i < jsonArray.length(); i++){
            Object object = jsonArray.get(i);
            JSONObject cargo = new JSONObject(object.toString());
            ingridients_count.put(cargo.getString("key"), cargo.getInt("value"));
        }

        for (var key: ingridients_count.keySet()){
            ingridients.put(key, 0);
        }

        int hobos = (int) jsonObject.get("hobos");
        int stealing_time = (int) jsonObject.get("stealing_time");
        int eating_time = (int) jsonObject.get("eating_time");

        HobosThread hobosThread = new HobosThread(hobos, stealing_time, eating_time, ingridients_count);

        statusThread.start();
        shipThread.start();
        docksThread.start();
        hobosThread.start();
    }
}