package by.Dzenia.docks_and_hobos.RunnableObjects;
import by.Dzenia.docks_and_hobos.Controller.Model;
import by.Dzenia.docks_and_hobos.CustomLogger;
import by.Dzenia.docks_and_hobos.RunnableObjects.StealingStrategy.StealingStrategy;
import by.Dzenia.docks_and_hobos.RunnableObjects.StealingStrategy.StealingStrategyHobByCargo;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hobos implements Runnable {
    private final Model model;
    private final Map<String, Integer> ingredientsCount;
    private final int eatingTime;
    final List<Hobo> hobos = new ArrayList<>();
    private final Map<String, Integer> current = new HashMap<>();
    private final Logger logger = CustomLogger.getLogger("all");
    private StealingStrategy stealingStrategy = new StealingStrategyHobByCargo();
    public Map<String, Integer> getIngredientsCount() {
        return ingredientsCount;
    }
    public int getEatingTime() {
        return eatingTime;
    }
    public List<Hobo> getHobos() {
        return hobos;
    }
    public Logger getLogger() {
        return logger;
    }
    public Hobos(Map<String, Integer> ingredientsCount, Map<String, Integer> hobosTimesToSteal, int eatingTime, Model model) {
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
        this.model = model;
    }
    public void setStealingStrategy(StealingStrategy stealingStrategy) {
        this.stealingStrategy = stealingStrategy;
    }

    @Override
    public void run() {
        while (true) {
            for (String cargo: ingredientsCount.keySet()) {
                current.put(cargo, 0);
            }
            stealingStrategy.goStealing(this);
            logger.info("Hobos start eating");
            try {
                Thread.sleep(eatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public class Hobo implements Runnable {
        private final int stealingSpeed;
        private final String name;
        private String cargoToSteal = null;
        public Hobo(int stealingSpeed, String name) {
            if (stealingSpeed <= 0) {
                throw new IllegalArgumentException("Time to steal must be positive");
            }
            this.stealingSpeed = stealingSpeed;
            this.name = name;
        }
        public void setCargoToSteal(String cargoToSteal) {
            this.cargoToSteal = cargoToSteal;
        }
        @Override
        public void run() {
            if (cargoToSteal == null) {
                return;
            }
            int pos = 0;
            logger.log(Level.INFO, name + " start stealing " + cargoToSteal);
            long startTime = System.currentTimeMillis();
            while (true) {
                Dock dock = model.getDocks().get(pos);
                pos = (pos + 1) % model.getDocks().size();
                while (true) {
                    Integer stole = dock.stealCargo(cargoToSteal, stealingSpeed);
                    try {
                        Thread.sleep((long) ((1.0 * stole / stealingSpeed) * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (current) {
                        current.put(cargoToSteal, current.get(cargoToSteal) + stole);
                        if (current.get(cargoToSteal) > ingredientsCount.get(cargoToSteal)) {
                            long endTime = System.currentTimeMillis();
                            logger.log(Level.INFO, name + " end stealing " + cargoToSteal + " time=" +
                                    (endTime - startTime) / 1000 + "s");
                            return;
                        }
                    }
                    if (stole == 0) {
                        break;
                    }
                }
            }
        }
    }
}
