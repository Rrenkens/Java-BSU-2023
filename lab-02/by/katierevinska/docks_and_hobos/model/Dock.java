package by.katierevinska.docks_and_hobos.model;

import by.katierevinska.docks_and_hobos.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dock implements Runnable {
    private Long UNLOADING_SPEED;
    private Map<String, Integer> dockCapacity;
    private ConcurrentHashMap<String, Long> currentNumOfIngredients;

    public Dock() {
        this.currentNumOfIngredients = new ConcurrentHashMap<>();
        for (var ing : Controller.getInstance().getModel().getCargoTypes()) {
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
        Thread.sleep( addingIngredients/ getUNLOADING_SPEED());
    }

    public synchronized boolean steelIngredient(String ing) {
        if (this.currentNumOfIngredients.get(ing) <= 0) {
            return false;
        }
        Long newValue = this.currentNumOfIngredients.get(ing) - 1;
        this.currentNumOfIngredients.put(ing, newValue);
        return true;
    }

    public void setUNLOADING_SPEED(Long UNLOADING_SPEED) {
        this.UNLOADING_SPEED = UNLOADING_SPEED;
    }

    public Long getUNLOADING_SPEED() {
        return this.UNLOADING_SPEED;
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
                    Ship shipForUploading = Controller.getInstance().getModel().getTunnel().sendToDock();
                    System.out.println("sending ship "+ shipForUploading.getShipId()+" to the dock");
                    addIngredient(shipForUploading.getCargoType(), shipForUploading.getShipCapacity());
                    System.out.println("dock uploaded ship "+ shipForUploading.getShipId());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

}
