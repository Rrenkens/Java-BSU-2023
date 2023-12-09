package by.katierevinska.docks_and_hobos;

import java.util.HashMap;
import java.util.Map;

public class Dock implements Runnable {
    private Long unloadingSpeed;//единиц товара в секунду
    private Map<String, Integer> dockCapacity;
    private Map<String, Long> currentNumOfIngredients;
    Process process;

    Dock(Process process) {
        this.process = process;
        this.currentNumOfIngredients = new HashMap<>();
        for (var ing : process.shipGenerator.cargoTypes) {
            this.currentNumOfIngredients.put(ing, 0L);
        }
    }

    public void addIngredient(String ing, Long num) {
        Long newValue = this.currentNumOfIngredients.get(ing) + num;
        this.currentNumOfIngredients.put(ing,
                newValue < dockCapacity.get(ing) ? newValue:dockCapacity.get(ing));
    }
    public boolean steelIngredient(String ing) {
        if(this.currentNumOfIngredients.get(ing) <= 0){
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


    public void run() {
        try {
            Ship shipForUploading = process.tunnel.sendToDock();
            addIngredient(shipForUploading.getCargoType(), shipForUploading.getShipCapacity());
            System.out.println("uploadedShip");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
