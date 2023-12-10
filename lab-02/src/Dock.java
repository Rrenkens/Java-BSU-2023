import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Dock implements Runnable {
    static final Object lock = new Object();
    long unloading_speed;
    final long[] dock_capacity;
    final AtomicLong[] curr_dock_capacities;
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    lock.wait();
                }
                Ship ship = Tunnel.tunnel.poll();
                if (ship != null) {
                    Tunnel.tunnel_size.getAndDecrement();
                    System.out.println(ship + " is unloading");
                    long type = ship.cargo_type;
                    long capacity = Math.min(ship.capacity, dock_capacity[(int) type] - curr_dock_capacities[(int) type].get());
                    Thread.sleep((long) ((double) capacity / unloading_speed * 1000));
                    System.out.println("Capacity: " + capacity + " of type: " + type + " was unloaded");
                    curr_dock_capacities[(int) type].addAndGet(capacity);
                    ship.capacity -= capacity;
                    System.out.println(ship + ", unloading is end");
                    synchronized (ship.lock) {
                        ship.lock.notify();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Oops Dock");
            Thread.currentThread().interrupt();
        }
    }

    Dock(long unloading_speed, long[] dock_capacity) {
        if (dock_capacity.length != Ship.cargo_types.length) {
            throw new IllegalArgumentException("Incorrect length of dock_capacities");
        }
        this.unloading_speed = unloading_speed;
        this.dock_capacity = Arrays.copyOf(dock_capacity, dock_capacity.length);
        curr_dock_capacities = new AtomicLong[dock_capacity.length];
        for (int i = 0; i < dock_capacity.length; ++i) {
            curr_dock_capacities[i] = new AtomicLong(0);
        }
    }
}
