package by.mnik0_0.docks_and_hobos;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HoboGroup implements Runnable {
    private HashMap<String, Integer> ingredientsCount;
    private HashMap<String, Integer> ingredientsWeNeed;
    private ArrayList<String> types = new ArrayList<>();
    private int hobos;
    private final Dock dock;
    private Random random = new Random();
    int stealingTime;
    int eatingTime;
    int startIndex;

    public HoboGroup(HashMap<String, Integer> ingredientsCount, int hobos, Dock dock, int stealingTime, int eatingTime) {
        this.ingredientsCount = ingredientsCount;
        this.hobos = hobos;
        this.dock = dock;
        this.ingredientsWeNeed = new HashMap<>(ingredientsCount);
        this.types.addAll(ingredientsCount.keySet());
        this.stealingTime = stealingTime;
        this.eatingTime = eatingTime;
    }

    public String getTypeToSteal() {
        int currentIndex = startIndex;
        startIndex = (startIndex + 1) % types.size();
        int counter = 0;

        while (counter < types.size()) {
            String type = types.get(currentIndex);
            int count = ingredientsWeNeed.get(type);
            if (count != 0) {
                ingredientsWeNeed.put(type, count - 1);
                return type;
            }
            counter++;
            currentIndex = (currentIndex + 1) % types.size();
        }

        return null;
    }

    public boolean readyToCook() {
        for (int counter : ingredientsWeNeed.values()) {
            if (counter != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {

        while (true) {

            int workers = hobos - 2;

            for (int i = 0; i < workers; i++) {
                String type = getTypeToSteal();
                if (type == null) {
                    continue;
                }
                if (!dock.getGoodByType(type)) {

                    int count = ingredientsWeNeed.get(type);
                    ingredientsWeNeed.put(type, count + 1);
                }
            }

            try {
                Thread.sleep(stealingTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("------------------------\n");
            stringBuilder.append("hobos in ").append(dock.getName()).append("\n");
            for (String t: ingredientsWeNeed.keySet()) {
                stringBuilder.append(t).append(" ").append(ingredientsWeNeed.get(t)).append("/").append(ingredientsCount.get(t)).append("\n");
            }
            stringBuilder.append("------------------------\n");
            System.out.println(stringBuilder);

            if (readyToCook()) {
                System.out.printf("Eating in %s%n", dock.getName());
                try {
                    Thread.sleep(eatingTime * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ingredientsWeNeed = new HashMap<>(ingredientsCount);
            }
        }

    }
}
