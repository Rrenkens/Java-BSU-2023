import java.util.Arrays;
import java.util.Objects;

public class Dock implements Runnable {
    static final Object lock = new Object();
    double unloading_speed;
    final double[] dock_capacities;
    final double[] curr_dock_capacities;
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    lock.wait();
                }
                Ship ship = Tunnel.getTunnel().poll();
                Tunnel.getTunnelSize().getAndDecrement();
                if (ship != null) {
                    System.out.println(ship + " is unloading");
                    int type = ship.getCargoType();
                    double capacity = Math.min(ship.getCapacity(), dock_capacities[type] - curr_dock_capacities[type]);
                    Thread.sleep((int) Math.ceil( capacity/ unloading_speed * 1000));
                    System.out.println("Capacity: " + capacity + " of type: " + type + " was unloaded");
                    curr_dock_capacities[type] += capacity;
                    ship.setCapacity(0, true);
                    System.out.println(ship + ", unloading is end");
                    synchronized (ship.lock) {
                        ship.lock.notify();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Ooops");
        }
    }

    Dock(double unloading_speed, double[] dock_capacities) {
        if (dock_capacities.length != Ship.cargo_types.length) {
            throw new IllegalArgumentException("Incorrect length of dock_capacities");
        }
        this.unloading_speed = unloading_speed;
        this.dock_capacities = Arrays.copyOf(dock_capacities, dock_capacities.length);
        this.curr_dock_capacities = new double[dock_capacities.length];
    }
}
