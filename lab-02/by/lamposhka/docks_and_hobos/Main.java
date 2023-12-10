package by.lamposhka.docks_and_hobos;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.TableHeaderUI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PrimitiveIterator;
import java.util.logging.Logger;

public class Main {
    private static Controller controller;
    public static void initializeController(String path) throws Exception {
        Object object = new JSONParser().parse(new FileReader(path));
        JSONObject jsonObject = (JSONObject) object;
        Tunnel tunnel = new Tunnel((int) (long) jsonObject.get("max_ships"));
        ArrayList<Dock> docks = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            docks.add(new Dock());
        }
        controller = new Controller(tunnel, new ShipGenerator(), docks);
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
