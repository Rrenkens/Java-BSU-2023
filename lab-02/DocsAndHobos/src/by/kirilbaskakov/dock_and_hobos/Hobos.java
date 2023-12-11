package by.kirilbaskakov.dock_and_hobos;

import java.util.*;

public class Hobos {
    private int hobos;
    private Map<String, Integer> ingredientsCount;
    private Map<String, Integer> ingredientsNeed;
    private int eatingTime;
    private int stealingTime;
    private Dock dock;
    private Thread stealingThread;
    private boolean isStealing;

    public Hobos(int hobos, Map<String, Integer> ingredientsCount, int eatingTime, int stealingTime, Dock dock) {
        this.hobos = hobos;
        this.ingredientsCount = ingredientsCount;
        this.ingredientsNeed = new HashMap<>(ingredientsCount);
        this.eatingTime = eatingTime;
        this.stealingTime = stealingTime;
        this.dock = dock;
        this.isStealing = false;
    }

    public synchronized boolean isCooked() {
        for (int count : ingredientsNeed.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public synchronized void stealFromDock(int hoboId) {
        List<String> availableCargoTypes = dock.getCargoTypes();

        List<String> availableIngredients = availableCargoTypes.stream()
                .filter(ingredient -> ingredientsNeed.containsKey(ingredient) && ingredientsNeed.get(ingredient) > 0)
                .toList();

        if (availableIngredients.isEmpty()) {
            return;
        }

        String ingredientToSteal = availableIngredients.get(new Random().nextInt(availableIngredients.size()));
        ingredientsNeed.put(ingredientToSteal, ingredientsNeed.get(ingredientToSteal) - 1);
        dock.stealCargo(ingredientToSteal);

        MyLogger.getInstance().info("Hobo " + hoboId + " stole 1 unit of " + ingredientToSteal);
    }

    public void startSteal() {
        if (isStealing) {
            MyLogger.getInstance().error("Hobos are already stealing");
            throw new RuntimeException("Hobos are already stealing");
        }
        isStealing = true;
        stealingThread = new Thread(() -> {
            while (true) {
                ArrayList<Integer> hobosId = new ArrayList<>();
                for (int i = 0; i < hobos; i++) {
                    hobosId.add(i);
                }
                Collections.shuffle(hobosId);
                MyLogger.getInstance().info("Hobos start stealing");
                MyLogger.getInstance().info("Hobos " + hobosId.get(hobos - 1) + " and " + hobosId.get(hobos - 2) + " are cooking");

                Hobo[] hobosTasks = new Hobo[hobos - 2];
                for (int i = 0; i + 2 < hobos; i++) {
                    hobosTasks[i] = new Hobo(hobosId.get(i), stealingTime, this);
                }
                Thread[] hobosThreads = new Thread[this.hobos - 2];
                for (int i = 0; i + 2 < this.hobos; i++) {
                    hobosThreads[i] = new Thread(hobosTasks[i]);
                }
                for (int i = 0; i + 2 < this.hobos; i++) {
                    hobosThreads[i].start();
                }
                for (int i = 0; i + 2 < this.hobos; i++) {
                    try {
                        hobosThreads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MyLogger.getInstance().info("Dish cooked. Hobos are eating.");
                ingredientsNeed = new HashMap<>(ingredientsCount);
                try {
                    Thread.sleep(eatingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        stealingThread.start();
    }
}

