package by.lamposhka.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hobos implements Runnable{
    private HashMap<String, Integer> ingredientsNeeded = new HashMap<>();
    private ConcurrentHashMap<String, Integer> ingredientsStolen = new ConcurrentHashMap<>();
    private int eatingTime;
    ArrayList<Hobo> hobos = new ArrayList<>();
    Logger logger = Logger.getLogger("HobosLogger");
    ArrayList<Thread> threads = new ArrayList<>();
    public Hobos(int hobos_count, HashMap<String, Integer> ingredients_count, int stealing_time, int eating_time){
        for (var ingredient: ingredients_count.keySet()) {
            ingredientsNeeded.put(ingredient, ingredients_count.get(ingredient));
            ingredientsStolen.put(ingredient, 0);
        }
        eatingTime = eating_time;
        for (int i = 0; i < hobos_count; ++i) {
            hobos.add(new Hobo(stealing_time));
        }
        for (Hobo hobo: hobos) {
            threads.add(new Thread(hobo));
        }
    }

    HashMap<String, Integer> getIngredientsNeeded () {
        return ingredientsNeeded;
    }

    public boolean isStealingTime() {
//        for (String key: ingredientsNeeded.keySet()) {
//            if ((int)ingredientsNeeded.get(key) != (int)ingredientsStolen.get(key)) {
//                logger.log(Level.INFO, "PZDC");
//                return false;
//            }
//        }
        synchronized (this) {
            return !ingredientsNeeded.equals(ingredientsStolen);

        }
//        return true;
    }

    @Override
    public void run() {
        for (var i : threads) {
            i.start();
        }

        while (true) {
            while (isStealingTime()) {
                String cargoToSteal = "";
                for (var key: ingredientsNeeded.keySet()) {
                    if (ingredientsNeeded.get(key) > ingredientsStolen.get(key)) {
                        cargoToSteal = key;
                        break;
                    }
                }
                for (Hobo hobo: hobos) {
                    if (!hobo.isBusy()) {
                        logger.log(Level.INFO, "assigned " + cargoToSteal);
                        hobo.setCargo(cargoToSteal);
                        ingredientsStolen.put(cargoToSteal, ingredientsStolen.get(cargoToSteal) + 1);
                        logger.log(Level.INFO, ingredientsNeeded + " " + ingredientsStolen);
                        break;
                    }
                }
            }

            boolean skip = false;
            for(var hobo: hobos) {
               if (hobo.isBusy()) {
                   skip = true;
                   break;
               }
            }
            if (skip) {
                continue;
            }
            try {
                logger.log(Level.INFO, "HOBOS BEGAN TO EAT!!!!!!");
                Thread.sleep(eatingTime);
                ingredientsStolen.replaceAll((k, v) -> 0);
                logger.log(Level.INFO, "HOBOS ATE!!!!!!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
