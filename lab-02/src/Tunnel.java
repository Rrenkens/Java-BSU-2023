import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Tunnel {
    long max_ships;
    static final ConcurrentLinkedQueue<Ship> tunnel = new ConcurrentLinkedQueue<>();
    static final AtomicInteger tunnel_size = new AtomicInteger(0);
    Tunnel(long max_ships) {
        this.max_ships = max_ships;
    }

    void enter(Ship ship) {
        if (tunnel_size.get() < max_ships) {
            tunnel_size.getAndIncrement();
            System.out.println(ship + " entered in tunnel");
            tunnel.add(ship);
            try {
                synchronized (ship.lock) {
                    ship.lock.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Oops");
                Thread.currentThread().interrupt();
            }
        } else {
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
