package by.katierevinska.docks_and_hobos;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Dock implements Runnable {
    private Long unloadingSpeed;//единиц товара в секунду
    private Map<String, Integer> dockCapacity;
    private ConcurrentHashMap<String, Long> currentNumOfIngredients;

    Dock() {
        this.currentNumOfIngredients = new ConcurrentHashMap<>();
        for (var ing : Process.getInstance().shipGenerator.cargoTypes) {
            this.currentNumOfIngredients.put(ing, 0L);
        }
    }

    public void addIngredient(String ing, Long num) throws InterruptedException {
        Long newValue = this.currentNumOfIngredients.get(ing) + num;
        Long ingredientsForUploading =  newValue < dockCapacity.get(ing) ? newValue : dockCapacity.get(ing);
        this.currentNumOfIngredients.put(ing,ingredientsForUploading
               );
        Long addingIngredients =  dockCapacity.get(ing) - this.currentNumOfIngredients.get(ing) > num ?
                num : dockCapacity.get(ing) - this.currentNumOfIngredients.get(ing);
        Thread.sleep( addingIngredients/ getUnloadingSpeed());
    }

    public synchronized boolean steelIngredient(String ing) {
        if (this.currentNumOfIngredients.get(ing) <= 0) {
            return false;
        }
        Long newValue = this.currentNumOfIngredients.get(ing) - 1;
        this.currentNumOfIngredients.put(ing, newValue);
        return true;
    }

    public void setUnloadingSpeed(Long unloadingSpeed) {
        this.unloadingSpeed = unloadingSpeed;
    }

    public Long getUnloadingSpeed() {
        return this.unloadingSpeed;
    }

    public void setDockCapacity(Map<String, Integer> dockCapacity) {
        this.dockCapacity = dockCapacity;
    }

    public Map<String, Integer> getDockCapacity() {
        return this.dockCapacity;
    }
    @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("in tunnel " + Process.getInstance().tunnel.sizeOfShips());
                    Ship shipForUploading = Process.getInstance().tunnel.sendToDock();
                    addIngredient(shipForUploading.getCargoType(), shipForUploading.getShipCapacity());
                    System.out.println("uploadedShip");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

}
