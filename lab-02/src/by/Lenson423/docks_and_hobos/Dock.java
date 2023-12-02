package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Math.min;

public class Dock implements Runnable{
    final int unloadingSpeed;
    final int[] dockCapacity;
    final AtomicIntegerArray currentCount;

    public Dock(int unloadingSpeed, int @NotNull [] dockCapacity) {
        if (unloadingSpeed < 0){
            throw new IllegalArgumentException("Unloading speed less then 0");
        }
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.currentCount = new AtomicIntegerArray(dockCapacity.length);
    }

    public void getFromShip(@NotNull Ship ship) throws InterruptedException {
        int index = Controller.getController().getModel().getCargoTypes().getByName(ship.getShipType());
        int current = currentCount.addAndGet(index,
                min(ship.getShipCapacity(), dockCapacity[index] - currentCount.get(index)));
        Thread.sleep((current - ship.getShipCapacity()) / unloadingSpeed * 1000L);
    }

    public synchronized boolean stealProduct(@NotNull String product) {
        int num = Controller.getController().getModel().getCargoTypes().getByName(product);
        if (currentCount.get(num) == 0) {
            return false;
        }
        currentCount.decrementAndGet(num);
        return true;
    }

    @Override
    public void run(){
        while (true){
            //ToDo
        }
    }
}
