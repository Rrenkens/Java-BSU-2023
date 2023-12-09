package by.katierevinska.docks_and_hobos.model;

import by.katierevinska.docks_and_hobos.Controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class GroupOfHobos implements Runnable {
    private Long HOBOS_EATING_TIME;
    private Long HOBOS_STEALING_TIME;
    private int HOBOS_COUNT;
    List<Hobo> hobosList;
    Map<String, Long> ingredientsCount;
    ConcurrentHashMap<String, Long> nowIngredientsCount;

    public class Hobo implements Runnable {
        private Long hoboId;
        private boolean isStealing = true;

        public void makeCooking() {
            this.isStealing = false;
        }

        Hobo(Long id){
            hoboId = id;
        }

        Optional<String> ingredientNeedToSteeling() {
            for (int i = 0; i < Controller.getInstance().getModel().getCargoTypes().size(); ++i) {
                String type = Controller.getInstance().getModel().getCargoTypes().get(i);
                if (nowIngredientsCount.get(type)
                        < ingredientsCount.get(type)) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }

        @Override
        public void run() {
            while (ingredientNeedToSteeling().isPresent()) {
                String ingredientToSteeling = ingredientNeedToSteeling().get();
                boolean flag = false;
                while (true) {
                    for (var dock : Controller.getInstance().getModel().getDocks()) {
                        if (dock.steelIngredient(ingredientToSteeling)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            Thread.sleep(HOBOS_STEALING_TIME);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Hobo " + hoboId + " steeled product " + ingredientToSteeling + " from dock");

                        Long newCount = nowIngredientsCount.get(ingredientToSteeling) + 1;
                        nowIngredientsCount.put(ingredientToSteeling, newCount);
                        System.out.println("Hobo " + hoboId + " brought "+ ingredientToSteeling);

                        break;
                    }
                }
            }
        }
    }

    public void setNullListIngredients(){
        this.nowIngredientsCount = new ConcurrentHashMap<>();
        for (var ing : Controller.getInstance().getModel().getCargoTypes()) {
            this.nowIngredientsCount.put(ing, 0L);
        }
    }

    public GroupOfHobos setHobosStealingTime(Long HOBOS_STEALING_TIME) {
        this.HOBOS_STEALING_TIME = HOBOS_STEALING_TIME;
        return this;
    }

    public GroupOfHobos setHobosEatingTime(Long hobosEatingTime) {
        this.HOBOS_EATING_TIME = hobosEatingTime;
        return this;
    }

    public GroupOfHobos setHOBOS_COUNT(int HOBOS_COUNT) {
        this.HOBOS_COUNT = HOBOS_COUNT;
        return this;
    }

    public GroupOfHobos setIngredientsCount(Map<String, Long> ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
        return this;
    }

    int getHOBOS_COUNT() {
        return this.HOBOS_COUNT;
    }

    public void generateHobos() {
        hobosList = new ArrayList<>();
        for (int i = 0; i < HOBOS_COUNT; ++i) {
            hobosList.add(new Hobo((long) i));
        }
        int cooker1 = ThreadLocalRandom.current().nextInt(0, HOBOS_COUNT);
        hobosList.get(cooker1).makeCooking();
        int tmp = ThreadLocalRandom.current().nextInt(1, HOBOS_COUNT - 1);
        hobosList.get((cooker1 + tmp) % HOBOS_COUNT).makeCooking();//TODO exception if already cooker

    }

    @Override
    public void run() {
        while (true) {
            int count = hobosList.size();
            Thread[] threadsOfHobos = new Thread[count - 2];
            for (int i = 2; i < count; i++) {
                threadsOfHobos[i - 2] = new Thread(hobosList.get(i));
            }//TODO change known about cookers
            for (Thread thread : threadsOfHobos) {
                thread.start();
            }

            for (Thread thread : threadsOfHobos) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Hobos steeled all they need");
            for (String cargoType : Controller.getInstance().getModel().getCargoTypes()) {
                nowIngredientsCount.put(cargoType, 0L);
            }

            try {
                System.out.println("Hobos eating!!!)))");
                Thread.sleep(HOBOS_EATING_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
