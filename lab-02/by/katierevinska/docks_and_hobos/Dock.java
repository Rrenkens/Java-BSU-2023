package by.katierevinska.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dock {
    private int unloadingSpeed;//единиц товара в секунду
    private int dockCapacity;
    private int hobosStealingTime;
    private int hobosEatingTime;
    Map<String, Integer> ingredientsCount= new HashMap();
    private int hobosCount;
    private List<Hobos> hobosList;
    Dock(){
        ingredientsCount.put("Tomatoes", 12);
        ingredientsCount.put("Bread", 18);
        ingredientsCount.put("Sausages", 10);
        ingredientsCount.put("Butter", 30);
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
