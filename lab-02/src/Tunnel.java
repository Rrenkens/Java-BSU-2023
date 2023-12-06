import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Tunnel {
    int max_ships;
    private static final ConcurrentLinkedQueue<Ship> tunnel = new ConcurrentLinkedQueue<>();
    private static final AtomicInteger tunnel_size = new AtomicInteger(0);
    Tunnel(int max_ships) {
        this.max_ships = max_ships;
    }

    static ConcurrentLinkedQueue<Ship> getTunnel() { return tunnel; }
    static AtomicInteger getTunnelSize() { return tunnel_size; }

    void enter(Ship ship) {
        if (tunnel_size.getAndIncrement() < max_ships) {
            System.out.println(ship + " entered in tunnel");
            tunnel.add(ship);
            try {
                synchronized (ship.lock) {
                    ship.lock.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Oops");
            }
        } else {
            Thread.currentThread().interrupt();
            System.out.println(ship + " sinked");
        }
        System.out.println("Work Ended");
    }
    void process() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!tunnel.isEmpty()) {
                synchronized (Dock.lock) {
                    Dock.lock.notify();
                }
            }
        }
    }

}
