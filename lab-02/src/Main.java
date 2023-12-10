import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    static double eps = 1e-6;
    static ArrayList<Ship> ships = new ArrayList<>();
    static ArrayList<Dock> docs = new ArrayList<>();
    static ArrayList<Hobos> hobos = new ArrayList<>();
    public static void main(String[] args) {
        try {
            Object obj = new JSONParser().parse(new FileReader(args[0]));
            JSONObject jo = (JSONObject) obj;

            JSONObject jtunnel = (JSONObject) jo.get("tunnel");
            Tunnel tunnel = new Tunnel(((Long) jtunnel.get("max_size")).intValue());

            JSONArray jcargo_types = (JSONArray) jo.get("cargo_types");
            int size = jcargo_types.size();
            Ship.cargo_types = new String[size];
            for (int i = 0; i < size; ++i) {
                Ship.cargo_types[i] = (String) jcargo_types.get(i);
            }

            Thread generator = new Thread(new Generator(jo));
            generator.start();

            JSONArray jdocs = (JSONArray) jo.get("docs");
            for (Object o: jdocs) {
                JSONObject jdock = (JSONObject) o;
                JSONArray jdock_capacity = (JSONArray) jdock.get("dock_capacity");
                size = jdock_capacity.size();
                long[] dock_capacity = new long[size];
                for (int i = 0; i < size; ++i) {
                    dock_capacity[i] = (long) jdock_capacity.get(i);
                }

                docs.add(new Dock((long) jdock.get("unloading_speed"), dock_capacity));
            }

            Hobos.eating_time = (long) jo.get("eating_time");

            JSONArray jingredients_count = (JSONArray) jo.get("ingredients_count");
            size = jingredients_count.size();
            Hobos.ingredients_count = new long[size];
            Hobos.curr_ingredients_count = new AtomicLong[size];
            for (int i = 0; i < size; ++i) {
                Hobos.ingredients_count[i] = (long) jingredients_count.get(i);
                Hobos.curr_ingredients_count[i] = new AtomicLong(0);
            }
            JSONArray jhobos = (JSONArray) jo.get("hobos");
            for (Object o : jhobos) {
                JSONObject jhobo = (JSONObject) o;
                hobos.add(new Hobos((long) jhobo.get("stealing_time")));
            }

            ArrayList<Thread> ship_threads = new ArrayList<>();
            ArrayList<Thread> dock_threads = new ArrayList<>();

            size = docs.size();
            for (int i = 0; i < size; ++i) {
                dock_threads.add(new Thread(docs.get(i)));
                dock_threads.get(i).start();
            }

            Thread process = new Thread(tunnel::process);
            Thread hobos = new Thread(Hobos::steal);
            process.start();
            hobos.start();

            size = ships.size();
            for (int i = 0; i < size || generator.isAlive(); size = ships.size()) {
                if (i < size) {
                    int finalI = i;
                    ship_threads.add(new Thread(() -> {
                        tunnel.enter(ships.get(finalI));
                    }));
                    ship_threads.get(finalI).start();
                    ++i;
                }
            }

            for (var thread : ship_threads) {
                try {
                    thread.join();
                } catch (InterruptedException exc) {
                    System.out.println("Oops");
                }
            }

            process.interrupt();

            for (var thread : dock_threads) {
                thread.interrupt();
            }

            hobos.interrupt();

            System.out.println("Interupted");
            for (var dock : docs) {
                System.out.println(Arrays.toString(dock.curr_dock_capacities));
            }
            System.out.println(Hobos.eat);
        } catch (IOException | ParseException exc) {
            System.out.println("Oops");
        }
    }
}