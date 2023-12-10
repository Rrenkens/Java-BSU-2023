package by.Bvr_Julia.docks_and_hobos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dock implements Runnable {
    public final Long unloading_speed;
    public final Map<String, Long> dock_capacity;
    private volatile Map<String, Long> amount;
    private volatile List<Ship> ships;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    Dock(List<Ship> ships, Long unloading_speed, Map<String, Long> dock_capacity) {
        this.dock_capacity = dock_capacity;
        this.unloading_speed = unloading_speed;
        this.ships = ships;
        this.amount = new HashMap<>();
        for (String key : dock_capacity.keySet()) {
            this.amount.put(key, (long) 0);
        }
        thread = new Thread(this, "Dock thread");
        System.out.println("Dock was made up. " + dock_capacity);
    }

    public void putSomeCargo(Long amount, String type) {
        Long tmp = this.amount.get(type) + amount;
        if (tmp.compareTo(dock_capacity.get(type)) < 0) {
            this.amount.remove(type);
            this.amount.put(type, tmp);
        } else {
            this.amount.remove(type);
            this.amount.put(type, dock_capacity.get(type));
        }
        System.out.println("Added cargo " + Long.toString(amount) + type);
    }

    public synchronized  Long getSomeCargo(Long amount, String type) {
        Long tmp = this.amount.get(type);
        if (tmp.compareTo(amount) > 0) {
            this.amount.remove(type);
            this.amount.put(type, tmp - amount);
            System.out.println("Cargo is stolen. " + Long.toString((amount)) + type+" Left " + this.amount);
            return amount;
        } else {
            this.amount.remove(type);
            this.amount.put(type, (long) 0);
            System.out.println("Cargo is stolen. " + Long.toString((tmp)) + type+ " Left " + this.amount);
            return tmp;
        }
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) / 1000 < Main.time) {
            Ship ship = null;
            try {
                ship = ships.getFirst();
                ships.removeFirst();
                System.out.println("Dock got a ship. " + Long.toString(ship.getCurrentAmount()) + ship.knowCargo());
                boolean tmp = true;
                while (tmp) {
                    Long cargo = ship.getSomeCargo(unloading_speed);
                    if (cargo.compareTo(unloading_speed) < 0) {
                        tmp = false;
                    }

                    Thread.sleep(1000);

                    cargo += amount.get(ship.knowCargo());
                    amount.remove(ship.knowCargo());
                    if (cargo.compareTo(dock_capacity.get(ship.knowCargo())) > 0) {
                        cargo = dock_capacity.get(ship.knowCargo());
                    }
                    amount.put(ship.knowCargo(), cargo);
                }
            } catch (InterruptedException e) {
                System.out.println("Dock has been interrupted\n");
            } catch (Exception ignored) {

            }


        }

        thread.interrupt();
    }


}
