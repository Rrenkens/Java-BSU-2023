package by.Bvr_Julia.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;

public class Tonnel implements Runnable {
    private final Long max_ships;
    private volatile List<Ship> ships;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    Tonnel(List<Ship> ships, Long max_ships) {
        this.ships = ships;
        this.max_ships = max_ships;
        thread = new Thread(this, "Tonnel thread");
        System.out.println("Tonnel was made up. " + Long.toString(max_ships));
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) / 1000 < Main.time) {
            if (max_ships.compareTo((long) ships.size()) < 0) {
                ships.removeLast();
                System.out.println("Tonnel deleted ship.\n");
            }
        }
        thread.interrupt();
    }
}
