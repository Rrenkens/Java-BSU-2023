package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class CargoTypes {
    private final HashMap<String, Integer> nameToIndex = new HashMap<>();
    public CargoTypes(@NotNull List<@NotNull String> names){
        if(names.isEmpty() || names.contains("")){
            throw new IllegalArgumentException("List with names is invalid");
        }
        int k = names.size();
        for (int i = 0; i < k; ++i){
            nameToIndex.put(names.get(i), i);
        }
    }

    public int getByName(@NotNull String name){
        return nameToIndex.get(name);
    }
}
