package by.busskov.docks_and_hobos;

import java.util.ArrayList;
import java.util.Arrays;

public class Bay {
    public static void main(String[] args) throws InterruptedException {
        Tunnel tunnel = new Tunnel(5);

        ArrayList<String> cargoTypes = new ArrayList<>(Arrays.asList("Guns", "Drugs", "Iphones", "Bananas"));
        Ship.Generator generator = new Ship.Generator(
                10,
                100,
                1000,
                cargoTypes,
                tunnel);

        Dock dock = new Dock(10, 100, cargoTypes, tunnel);

        Thread dockThread = new Thread(dock, "Dock");
        Thread generatorThread = new Thread(generator, "Ship generator");
        generatorThread.start();
        dockThread.start();

        generatorThread.join();
        dockThread.join();
    }
}
