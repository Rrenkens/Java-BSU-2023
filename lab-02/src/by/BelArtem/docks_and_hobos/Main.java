package by.BelArtem.docks_and_hobos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Ship> shipArrayList = new ArrayList<>();
        ShipGenerator generator = new ShipGenerator(1, 2, 3
        , shipArrayList);

        Thread generateThread = new Thread(generator, "Generator");
        generateThread.start();

        Tunnel tunnel = new Tunnel(5);
        TunnelManager manager = new TunnelManager(tunnel, generator);

        Thread managerThread = new Thread(manager);
        managerThread.start();


        //Thread.sleep(5000);


//        try{
//            generateThread.join();
//        } catch (InterruptedException e) {
//            System.out.println("During joining something went wrong");
//            throw new RuntimeException(e);
//        }

    }
}
