package by.kirilbaskakov.dock_and_hobos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class Dock {
    private int unloadingSpeed;
    private int dockCapacity;
    private Tunnel tunnel;
    private Thread unloadingThread;
    private boolean isUnloading;
    private Map<String, Integer> cargoCounts;

    public Dock(int unloadingSpeed, int dockCapacity, Tunnel tunnel) {
        if (unloadingSpeed <= 0) {
            MyLogger.getInstance().error("Unloading speed must be positive");
            throw new RuntimeException("Unloading speed must be positive");
        }
        if (dockCapacity <= 0) {
            MyLogger.getInstance().error("Dock capacity must be positive");
            throw new RuntimeException("Dock capacity must be positive");
        }
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.tunnel = tunnel;
        this.unloadingThread = null;
        this.isUnloading = false;
        this.cargoCounts = new ConcurrentHashMap<>();
    }

    public void startUnloading() {
        if (isUnloading) {
            MyLogger.getInstance().error("Dock is already unloading.");
            throw new RuntimeException("Dock is already unloading.");
        }

        isUnloading = true;
        unloadingThread = new Thread(() -> {
            while (true) {
                Ship ship = tunnel.getFirstShip();
                if (ship != null) {
                    int cargoToUnload = Math.min(unloadingSpeed, ship.getCargoCapacity());
                    ship.unloadCargo(cargoToUnload);

                    String cargoType = ship.getCargoType();

                   MyLogger.getInstance().info("Unloaded " + cargoToUnload + " units of " + ship.getCargoType() + " from ship " + ship.getId());

                    int currentCargoCount = cargoCounts.getOrDefault(cargoType, 0);
                    int newCargoCount = Math.min(dockCapacity, currentCargoCount + cargoToUnload);
                    cargoCounts.put(cargoType, newCargoCount);

                    MyLogger.getInstance().info(newCargoCount - currentCargoCount + " units of " + cargoType + " were added to stockpile");

                    if (ship.getCargoCapacity() == 0) {
                        MyLogger.getInstance().info("Ship " + ship.getId() + " fully unloaded.");
                        tunnel.removeFirstShip();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        unloadingThread.start();
    }

    public ArrayList<String> getCargoTypes() {
        ArrayList<String> cargoTypes = new ArrayList<>();
        for (String cargo : cargoCounts.keySet())  {
            if (cargoCounts.get(cargo) > 0) {
                cargoTypes.add(cargo);
            }
        }
        return cargoTypes;
    }

    public void stealCargo(String cargo) {
        cargoCounts.put(cargo, cargoCounts.get(cargo) - 1);
    }


}