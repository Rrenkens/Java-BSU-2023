package by.katierevinska.docks_and_hobos;


import com.sun.source.doctree.TextTree;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread;


public class Main {
    public static void main(String[] args) {
        ShipGenerator shipGenerator = new ShipGenerator();
        Tunnel tunnel = new Tunnel();
        Dock dock = new Dock();

        try {
            JSONObject configParam = getJsonObject();
            shipGenerator.setGeneratingTime((Long) configParam.get("generating_time"))
                    .setShipCapacityMin((Long) configParam.get("ship_capacity_min"))
                    .setShipCapacityMax((Long) configParam.get("ship_capacity_max"));
            tunnel.setMaxShips((Long) configParam.get("max_ships"));
            dock.setUnloadingSpeed((Long)configParam.get("unloading_speed"));
            Thread threadGenerationShips = new Thread(() -> {
                while (true) {
                    tunnel.setShip(shipGenerator.generate());
                    System.out.println("generate ship on tunnel which size is "+tunnel.sizeOfShips()
                            +"and max size is over "+tunnel.isFull());
                    try {
                        Thread.sleep(shipGenerator.getGeneratingTime());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Thread threadTunnel = new Thread(() -> {
                while (true) {
                    if (tunnel.isFull()) {
                        tunnel.sinkShip();
                        System.out.println("sink lastIn ship");
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Thread threadDock = new Thread(() -> {
                while (true) {
                    if (!tunnel.isEmpty()) {
                        Ship ship = tunnel.sendToDock();
                        System.out.println("send firstIn ship to dock");
                        dock.addVolume(ship.getShipCapacity());
                        try {
                            Thread.sleep(dock.getUnloadingSpeed());
                            System.out.println("dock unloaded ship");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            threadGenerationShips.start();
            threadTunnel.start();
            threadDock.start();

        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

    private static JSONObject getJsonObject() throws IOException, ParseException {
        Object object = new JSONParser().parse(new FileReader("config.json"));
        return (JSONObject) object;
    }
}
