package by.mnik0_0.docks_and_hobos;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


class RootObject {
    class Tunnel {
        @SerializedName("maxShips")
        public int maxShips;
    }

    class ShipGenerator {
        @SerializedName("generatingTime")
        public int generatingTime;

        @SerializedName("shipCapacityMin")
        public int shipCapacityMin;

        @SerializedName("shipCapacityMax")
        public int shipCapacityMax;
    }

    class Dock {

        class HoboGroup {
            @SerializedName("hobos")
            public int hobos;

            @SerializedName("stealingTime")
            public int stealingTime;

            @SerializedName("eatingTime")
            public int eatingTime;

            @SerializedName("ingredients")
            public HashMap<String, Integer> ingredients;
        }

        @SerializedName("unloadingSpeed")
        public int unloadingSpeed;

        @SerializedName("dockCapacity")
        public int dockCapacity;

        @SerializedName("name")
        public String name;

        @SerializedName("hoboGroup")
        public HoboGroup hoboGroup;
    }

    @SerializedName("types")
    public String[] types;

    @SerializedName("tunnel")
    public Tunnel tunnel;

    @SerializedName("shipGenerator")
    public ShipGenerator shipGenerator;

    @SerializedName("docks")
    public Dock[] docks;
}


public class Program {
    public static void main(String[] args) {

        Gson gson = new Gson();
        RootObject rootObject;
        try {
            FileReader reader = new FileReader("lab-02/by/mnik0_0/docks_and_hobos/config.json");
            rootObject = gson.fromJson(reader, RootObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // TUNNEL
        Tunnel tunnel = new Tunnel(rootObject.tunnel.maxShips);

        // SHIP GENERATOR
        int generatingTime = rootObject.shipGenerator.generatingTime;
        int shipCapacityMin = rootObject.shipGenerator.shipCapacityMin;
        int shipCapacityMax = rootObject.shipGenerator.shipCapacityMax;
        ArrayList<String> cargoTypes = new ArrayList<>(
                List.of(rootObject.types)
        );

        ShipGenerator shipGenerator = new ShipGenerator(generatingTime, shipCapacityMin, shipCapacityMax, cargoTypes, tunnel);

        // DOCKS AND GROUPS
        ArrayList<Dock> docks = new ArrayList<>();
        ArrayList<HoboGroup> hoboGroups = new ArrayList<>();
        for (RootObject.Dock d: rootObject.docks) {
            int unloadingSpeed = d.unloadingSpeed;
            int dockCapacity = d.dockCapacity;
            String name = d.name;
            Dock dock = new Dock(unloadingSpeed, dockCapacity, tunnel, name, cargoTypes);
            docks.add(dock);

            HashMap<String, Integer> ingredientsCount = d.hoboGroup.ingredients;
            int hobos = d.hoboGroup.hobos;
            int stealingTime = d.hoboGroup.stealingTime;
            int eatingTime = d.hoboGroup.eatingTime;
            HoboGroup group = new HoboGroup(ingredientsCount, hobos, dock, stealingTime, eatingTime);
            hoboGroups.add(group);
        }

        Thread shipGeneratorThread = new Thread(shipGenerator);
        shipGeneratorThread.start();

        for (Dock dock: docks) {
            Thread dockThread = new Thread(dock);
            dockThread.start();
        }

        for (HoboGroup hoboGroup: hoboGroups) {
            Thread hoboGroupThread = new Thread(hoboGroup);
            hoboGroupThread.start();
        }

    }
}
