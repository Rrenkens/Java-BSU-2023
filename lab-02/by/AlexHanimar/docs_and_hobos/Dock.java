package by.AlexHanimar.docs_and_hobos;

import by.AlexHanimar.docs_and_hobos.Product;
import by.AlexHanimar.docs_and_hobos.Ship;

import java.util.Map;
import java.util.HashMap;

import static java.lang.Math.min;
import static java.lang.Thread.sleep;

public class Dock {
    private final Map<String, Integer> capacity;
    private final Map<String, Integer> count;

    public Dock(Map<String, Integer> capacity) {
        this.capacity = capacity;
        count = new HashMap<>();
        for (var key : capacity.keySet()) {
            count.put(key, 0);
        }
    }

    public Map<String, Integer> getCapacity() {
        return capacity;
    }

    public Map<String, Integer> getCount() {
        return count;
    }
}
