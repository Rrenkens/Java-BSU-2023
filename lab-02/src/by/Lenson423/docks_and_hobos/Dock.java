package by.Lenson423.docks_and_hobos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Math.min;

public class Dock {
    final int unloadingSpeed;
    final int[] dockCapacity;
    final AtomicIntegerArray currentCount;

    final CargoTypes cargoTypes = new CargoTypes(new ArrayList<>()); //ToDo

    public Dock(int unloadingSpeed, int[] dockCapacity) {
        if (unloadingSpeed < 0){
            throw new IllegalArgumentException("Unloading speed less then 0");
        }
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.currentCount = new AtomicIntegerArray(dockCapacity.length);
    }

    public void getFromShip(Ship ship) throws InterruptedException {
        if (ship == null){
            throw new IllegalArgumentException("No ship exception");
        }
        int index = cargoTypes.getByName(ship.getShipType());
        int current = currentCount.addAndGet(index,
                min(ship.getShipCapacity(), dockCapacity[index] - currentCount.get(index)));
        Thread.sleep((current - ship.getShipCapacity()) / unloadingSpeed * 1000L);
    }

    public void startWorking(){
        //ToDo
    }
}
