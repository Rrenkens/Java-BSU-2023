package by.Dzenia.docks_and_hobos.RunnableObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hobos implements Runnable{

    private final Map<String, Integer> ingredientsCount;
    private final int eatingTime;
    final List<Hobo> hobos = new ArrayList<>();

    public Hobos(Map<String, Integer> ingredientsCount, Map<String, Integer> hobosTimesToSteal, int eatingTime) {
        if (eatingTime < 0) {
            throw new IllegalArgumentException("Eating time cannot be negative");
        }
        if (!ingredientsCount.values().stream().allMatch(number -> number >= 0)) {
            throw new IllegalArgumentException("Ingredient count cannot be negative");
        }
        if (!hobosTimesToSteal.values().stream().allMatch(number -> number >= 0)) {
            throw new IllegalArgumentException("Hobo time to steal cannot be negative");
        }
        if (hobosTimesToSteal.values().size() <= 2) {
            throw new IllegalArgumentException("Hobos group too little");
        }
        for (String hoboName: hobosTimesToSteal.keySet()) {
            hobos.add(new Hobo(hobosTimesToSteal.get(hoboName), hoboName));
        }
        this.ingredientsCount = ingredientsCount;
        this.eatingTime = eatingTime;
    }

    @Override
    public void run() {

    }
    private class Hobo implements Runnable {
        private final int timeToSteal;
        private final String name;

        public Hobo(int timeToSteal, String name) {
            if (timeToSteal <= 0) {
                throw new IllegalArgumentException("Time to steal must be positive");
            }
            this.timeToSteal = timeToSteal;
            this.name = name;
        }

        @Override
        public void run() {
        }
    }
}
