package by.KseniyaGnezdilova.docks_and_hobos.threads;

import by.KseniyaGnezdilova.docks_and_hobos.Main;
import by.KseniyaGnezdilova.docks_and_hobos.Ship;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class DocksThread extends Thread{
    private final int unloading_speed;
    private final HashMap<String, Integer> dock_capacity;
    private Ship cur_ship;
    public DocksThread(int unloading_speed, HashMap<String, Integer> dock_capacity){
        this.unloading_speed = unloading_speed;
        this.dock_capacity = dock_capacity;
        this.cur_ship = null;
    }

    @Override
    public void run(){
        while (true){
            try {
                Main.sem.acquire(Main.sem.availablePermits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (Main.tunnel.isEmpty()) {
                Main.sem.release(1);
                try {
                    sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (cur_ship == null) {
                System.out.println('\n');
                this.cur_ship = Main.tunnel.get(0);

                try {
                    Main.sem.release(1);
                    sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (cur_ship.getShip_capacity() > 0 && cur_ship != null) {
                cur_ship.writeShip();
                cur_ship.setShip_capacity(cur_ship.getShip_capacity() - unloading_speed);
                String type = cur_ship.getCargo_type();
                Main.dock.replace(type, Math.min(Main.dock.get(type) + unloading_speed, dock_capacity.get(type)));
                if (cur_ship.getShip_capacity() <= 0) {
                    Main.tunnel.removeFirst();
                    cur_ship = null;
                }
                Main.sem.release(1);
                try {
                    sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
