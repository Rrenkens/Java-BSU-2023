package by.MikhailShurov.docks_and_hobos;
import java.util.HashMap;
import java.util.logging.Logger;

public class Dock implements Runnable{
    private static final Logger logger = LoggerUtil.getLogger();
    double unloadingAmountPerSecond_;
    Ship unloadingShip_;
    HashMap<String, Double> storageCapacity_ = new HashMap<>();
    HashMap<String, Double> maximumStorageCapacity_ = new HashMap<>();
    public Dock (double unloadingSpeed, double maximumStorageCapacity) {
        unloadingAmountPerSecond_ = unloadingSpeed;
        storageCapacity_.put("cocaine", 0.0);
        storageCapacity_.put("heroin", 0.0);
        storageCapacity_.put("marijuana", 0.0);
        storageCapacity_.put("pineapple", 0.0);

        maximumStorageCapacity_.put("cocaine", maximumStorageCapacity);
        maximumStorageCapacity_.put("heroin", maximumStorageCapacity);
        maximumStorageCapacity_.put("marijuana", maximumStorageCapacity);
        maximumStorageCapacity_.put("pineapple", maximumStorageCapacity);

        unloadingShip_ = null;
    }

    synchronized void setShip (Ship ship) {
        unloadingShip_ = ship;
    }

    Double getCargoCapacity(String cargoType) {
        return storageCapacity_.get(cargoType);
    }

    void stealCargo(String cargoType) {
        storageCapacity_.put(cargoType, storageCapacity_.get(cargoType) - 1);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (unloadingShip_ != null) {
                    while (unloadingShip_.getCapacity() > 0) {
                        double canBeUnloaded = unloadingShip_.unload(unloadingAmountPerSecond_);
                        if (storageCapacity_.get(unloadingShip_.getCargoType()) + unloadingAmountPerSecond_ < maximumStorageCapacity_.get(unloadingShip_.getCargoType())) {
                            storageCapacity_.put(unloadingShip_.getCargoType(), storageCapacity_.get(unloadingShip_.getCargoType()) + canBeUnloaded);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    logger.info("Ship successfully unloaded, dock free\n");
                    System.out.println("DOCK: Ship successfully unloaded, dock free");
                    unloadingShip_ = null;
                } else {
                    continue;
                }
            }
        }
    }

    boolean shipIsNull() {
        return unloadingShip_ == null;
    }
}