package by.Lenson423.docks_and_hobos;

import java.util.HashMap;
import java.util.List;

public class CargoTypes {
    HashMap<String, Integer> nameToIndex = new HashMap<>();
    public CargoTypes(List<String> names){
        if(names.isEmpty() || names.contains(null) || names.contains("")){
            throw new IllegalArgumentException("List with names is invalid");
        }
        int k = names.size();
        for (int i = 0; i < k; ++i){
            nameToIndex.put(names.get(i), i);
        }
    }

    public int getByName(String name){
        return nameToIndex.get(name);
    }
}
