package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Dock implements Runnable{

    private final int unloadingSpeed;
    private final int dockCapacity;

    private AtomicIntegerArray stock;

    private final TunnelManager tunnelManager;

    private final ArrayList<String> cargo_types;

    public Dock(int unloadingSpeed, int dockCapacity,
                TunnelManager tunnelManager, ArrayList<String> cargo_types) {
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        stock = new AtomicIntegerArray(cargo_types.size());
        this.tunnelManager = tunnelManager;
        this.cargo_types = cargo_types;
    }

    @Override
    public void run() {
        while(true) {
            Ship ship = tunnelManager.getFirstShip();
            if (ship == null) {
                continue;
            }
            System.out.println("New ship info: ");
            System.out.println("Cargo: " + ship.getCargoType() +
            "; capacity: " + ship.getCapacity());
            String cargoType = ship.getCargoType();
            int index = cargo_types.indexOf(cargoType);
            while (ship.getCargoLeft() != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                int itemsToPut = Math.min(ship.getCargoLeft(), this.unloadingSpeed);
                this.stock.set(index, Math.min(this.dockCapacity,
                        this.stock.get(index) + itemsToPut));
                ship.unloadShip(itemsToPut);
                System.out.println("Ship info: " + ship.getCargoLeft() + " cargo left");
                System.out.println("The dock " + Thread.currentThread().getName() + " info:");
                for (int i = 0; i < cargo_types.size(); ++i) {
                    System.out.println(this.stock.get(i));
                }
                System.out.println("End of info\n");
            }
            System.out.println("===========================");

        }

    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public int getDockCapacity() {
        return dockCapacity;
    }
}
