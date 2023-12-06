import java.util.ArrayList;

public class Main {
    static double eps = 1e-6;
    public static void main(String[] args) {
        Tunnel tunnel = new Tunnel(3);
        int ship_size = 5;
        ArrayList<Ship> ships = new ArrayList<>(5);
        ships.add(new Ship(50, "1"));
        ships.add(new Ship(40, "2"));
        ships.add(new Ship(60, "3"));
        ships.add(new Ship(80, "1"));
        ships.add(new Ship(70, "2"));
        ArrayList<Thread> ship_threads = new ArrayList<>(5);
        int dock_size = 2;
        ArrayList<Thread> dock_threads = new ArrayList<>(2);
        for (int i = 0; i < dock_size; ++i) {
            dock_threads.add(new Thread(new Dock(i % 2 == 0 ? 10 : 8, new double[]{50, 60, 40})));
            dock_threads.get(i).start();
        }
        Thread process = new Thread(tunnel::process);
        process.start();
        for (int i = 0; i < ship_size; ++i) {
            int finalI = i;
            ship_threads.add(new Thread(() -> {
                tunnel.enter(ships.get(finalI));
            }));
            ship_threads.get(finalI).start();
        }
        for (var thread : ship_threads) {
            try {
                thread.join();
            } catch (InterruptedException exc) {
                System.out.println("Oops");
            }
        }
        process.interrupt();
        for (var thread : dock_threads) {
            thread.interrupt();
        }
    }
}