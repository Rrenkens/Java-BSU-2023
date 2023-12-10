package by.KseniyaGnezdilova.docks_and_hobos.threads;

import by.KseniyaGnezdilova.docks_and_hobos.Main;
import by.KseniyaGnezdilova.docks_and_hobos.Ship;

public class StatusThread extends Thread{
    public StatusThread(){
    }

    @Override
    public void run(){
        while (true) {
            try {
                Main.sem.acquire(Main.sem.availablePermits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.print("Docks now >>> ");
            System.out.println(Main.dock.toString());

            System.out.print("Ingridients now >>> ");
            System.out.println(Main.ingridients.toString());

            System.out.println("Tunnel now >>> ");
            for (Ship obj : Main.tunnel) {
                obj.writeShip();
            }

            Main.sem.release();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
