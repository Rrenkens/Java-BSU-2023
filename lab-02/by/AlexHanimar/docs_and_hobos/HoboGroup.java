package by.AlexHanimar.docs_and_hobos;

import by.AlexHanimar.docs_and_hobos.Hobo;
import java.util.Collections;

import java.util.*;

public class HoboGroup implements HoboGroupInterface {
    private final List<Hobo> hobos;
    private final Random rng;
    private final Map<String, Integer> recipe;
    private final HashMap<String, Integer> supply;

    HoboGroup(Dock dock, int hobos, int stealingTime, Map<String, Integer> recipe, Random rng) {
        this.hobos = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < hobos; ++i) {
            this.hobos.add(new Hobo(String.valueOf(i), dock, this, stealingTime, rng));
        }
        this.rng = rng;
        this.recipe = recipe;
        this.supply = new HashMap<>();
        for (var key : recipe.keySet()) {
            supply.put(key, 0);
        }
    }

    public Map<String, Integer> getRecipe() {
        return recipe;
    }

    public HashMap<String, Integer> getSupply() {
        return supply;
    }

    synchronized int getFreeHoboCount() {
        return (int) hobos.stream().filter(Hobo::isFree).count();
    }

    synchronized int getHoboCount() {
        return hobos.size();
    }

    synchronized void clearFlags() {
        for (var hobo : hobos) {
            hobo.setFree(true);
            hobo.setCooking(false);
        }
    }

    synchronized void setCookers() {
        int id1 = rng.nextInt(hobos.size());
        int id2 = id1;
        while (id2 == id1) {
            id2 = rng.nextInt(hobos.size());
        }
        hobos.get(id1).setCooking(true);
        hobos.get(id2).setCooking(true);
    }

    synchronized Optional<Hobo> getFreeHobo() {
        var list = hobos.stream().filter(Hobo::isFree).filter(x -> !x.isCooking()).toList();
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            var result = list.get(rng.nextInt(list.size()));
            return Optional.of(result);
        }
    }

    boolean canCook() {
        for (var key : recipe.keySet()) {
            if (supply.get(key) < recipe.get(key)) {
                return false;
            }
        }
        return true;
    }

    void cook() {
        for (var key : recipe.keySet()) {
            var val = supply.get(key);
            supply.replace(key, val - recipe.get(key));
        }
    }
}
