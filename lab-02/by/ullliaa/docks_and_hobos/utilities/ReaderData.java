package by.ullliaa.docks_and_hobos.utilities;

import by.ullliaa.docks_and_hobos.actors.*;
import by.ullliaa.docks_and_hobos.actors.ships.ShipGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ReaderData {
    public static Model readData(String filename) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject info = (JSONObject) parser.parse(new FileReader(filename));

        JSONObject generator = (JSONObject) info.get("ShipGenerator");
        int generatingTime = (int) (long) generator.get("generatingTime");
        int minShipCapacity = (int) (long) generator.get("minShipCapacity");
        int maxShipCapacity = (int) (long) generator.get("maxShipCapacity");
        JSONArray jsonCargoTypes = (JSONArray) generator.get("cargoTypes");

        Vector<String> cargoTypes = new Vector<>();

        for (Object cargoType : jsonCargoTypes) {
            cargoTypes.add(cargoType.toString());
        }

        JSONObject jsonTunnel = (JSONObject) info.get("Tunnel");
        int maxShips = (int) (long) jsonTunnel.get("maxShips");

        Tunnel tunnel = new Tunnel(maxShips);
        ShipGenerator shipGenerator = new ShipGenerator(generatingTime, minShipCapacity, maxShipCapacity, cargoTypes, tunnel);

        JSONArray jsonDocks = (JSONArray) info.get("Docks");

        ArrayList<Dock> docks = new ArrayList<>();

        for (int i = 0; i < jsonDocks.size(); ++i) {
            JSONObject jsonDock = (JSONObject) jsonDocks.get(i);
            int unloadingSpeed = (int) (long) jsonDock.get("unloadingSpeed");

            int[] maxProductCapacity = new int[cargoTypes.size()];

            JSONObject jsonCapacity = (JSONObject) jsonDock.get("maxProductCapacity");
            for (int j = 0; j < jsonCapacity.size(); ++j) {
                try {
                    String elem = jsonCapacity.get(cargoTypes.get(j)).toString();
                    maxProductCapacity[j] = Integer.parseInt(elem);
                } catch (Exception e) {
                    throw new RuntimeException("Can't parse. Add capacity in dock for all cargos");
                }
            }
            docks.add(new Dock(unloadingSpeed, maxProductCapacity, i));
        }

        JSONArray jsonHobos = (JSONArray) info.get("Hobos");

        ArrayList<Hobos.Hobo> hoboArrayList = new ArrayList<>();
        for (int i = 0; i < jsonHobos.size(); ++i) {
            JSONObject jsonHobo = (JSONObject) jsonHobos.get(i);
            hoboArrayList.add(new Hobos.Hobo((int) (long) jsonHobo.get("stealingTime"), i));
        }

        int eatingTime = (int) (long) info.get("eatingTime");

        JSONObject jsonIngredients = (JSONObject) info.get("ingredientsCount");

        int[] ingredientsCount = new int[cargoTypes.size()];

        for (int i = 0; i < cargoTypes.size(); ++i) {
            try {
                ingredientsCount[i] = (int) (long) jsonIngredients.get(cargoTypes.get(i));
            } catch (Exception e) {
                throw new RuntimeException("Can't parse. Add count of all cargos, that hobos need");
            }
        }

        Hobos hobos = new Hobos(ingredientsCount, eatingTime, hoboArrayList);

        return new Model(docks, hobos, shipGenerator, tunnel, cargoTypes);
    }
}