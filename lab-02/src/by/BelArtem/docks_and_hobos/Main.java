package by.BelArtem.docks_and_hobos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ArrayList<Ship> shipArrayList = new ArrayList<>();
//        ShipGenerator generator = new ShipGenerator(1, 2, 3
//        , shipArrayList);

        ArrayList<String> cargo_types = new ArrayList<>();
        cargo_types.add("first");
        cargo_types.add("second");
        cargo_types.add("third");


        ShipGenerator generator = new ShipGenerator(1, 1, 10, cargo_types);

        Thread generateThread = new Thread(generator, "Generator");
        generateThread.start();

        Tunnel tunnel = new Tunnel(5);
        TunnelManager tunnelManager = new TunnelManager(tunnel, generator);

        Thread tunnelManagerThread = new Thread(tunnelManager);
        tunnelManagerThread.start();


        List<Dock> lst = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 2; ++i) {
            lst.add(new Dock(5, 23, tunnelManager, cargo_types));
        }
        DocksManager docksManager = new DocksManager(lst);

        Thread dockManagerThread = new Thread(docksManager);
        dockManagerThread.start();


        //Thread.sleep(5000);


//        try{
//            generateThread.join();
//        } catch (InterruptedException e) {
//            System.out.println("During joining something went wrong");
//            throw new RuntimeException(e);
//        }

    }
}
