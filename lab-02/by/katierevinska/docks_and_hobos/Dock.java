package by.katierevinska.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dock {
    private Long unloadingSpeed;//единиц товара в секунду
    private Long dockCapacity;
    private Long currentVolume = 0L;
    private Long hobosStealingTime;
    private Long hobosEatingTime;
    Map<String, Integer> ingredientsCount= new HashMap();
    private int hobosCount;
    private List<Hobos> hobosList;
    Dock(){
        ingredientsCount.put("Tomatoes", 12);
        ingredientsCount.put("Bread", 18);
        ingredientsCount.put("Sausages", 10);
        ingredientsCount.put("Butter", 30);
    }
    public void addVolume(Long volumeOfCargo) {
        this.currentVolume += volumeOfCargo;
    }
    public void setUnloadingSpeed(Long unloadingSpeed) {
        this.unloadingSpeed = unloadingSpeed;
    }
    public Long getUnloadingSpeed() {
        return this.unloadingSpeed;
    }
    public void setDockCapacity(Long dockCapacity) {
        this.dockCapacity = dockCapacity;
    }
    public Long getDockCapacity() {
        return this.dockCapacity;
    }
    public void generateHobos(){
        hobosList = new ArrayList<>();
        for(int i = 0; i < hobosCount; ++i){
            hobosList.add(new Hobos());
        }
    }
    public void generateProcessOnDock(){
        //threads
    }
}
