package by.KseniyaGnezdilova.docks_and_hobos.threads;

import by.KseniyaGnezdilova.docks_and_hobos.Main;
import by.KseniyaGnezdilova.docks_and_hobos.Ship;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class ShipThread extends Thread{
    private final int generating_time;
    private final int ship_capacity_min;
    private final int ship_capacity_max;
    private final Vector<String> cargo_types;

    public ShipThread(int generating_time, int ship_capacity_min, int ship_capacity_max, Vector<String> cargo_types){
        this.ship_capacity_min = ship_capacity_min;
        this.generating_time = generating_time;
        this.ship_capacity_max = ship_capacity_max;
        this.cargo_types = cargo_types;
    }
    public void addShip(Ship ship){
        if (Main.tunnel.size() < Main.max_ships){
            Main.tunnel.add(ship);
        }
    }
    @Override
    public void run(){
        while (true) {
            try {
                Main.sem.acquire(Main.sem.availablePermits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Random random = new Random();
            int capacity = random.nextInt(ship_capacity_max - ship_capacity_min + 1) + ship_capacity_min;
            int cargo = random.nextInt(cargo_types.size());
            Ship ship = new Ship(capacity, cargo_types.get(cargo));
            addShip(ship);
            try {
                Main.sem.release();
                sleep(generating_time * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
