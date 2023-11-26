package by.BelArtem.docks_and_hobos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Ship> shipArrayList = new ArrayList<>();
        ShipGenerator generator = new ShipGenerator(2, 2, 3
        , shipArrayList);

        Thread generateThread = new Thread(generator, "Generator");
        generateThread.start();

        //Thread.sleep(5000);


//        try{
//            generateThread.join();
//        } catch (InterruptedException e) {
//            System.out.println("During joining something went wrong");
//            throw new RuntimeException(e);
//        }

    }
}
