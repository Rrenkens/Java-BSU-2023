package by.katierevinska.docks_and_hobos.model;

import by.katierevinska.docks_and_hobos.Controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class GroupOfHobos implements Runnable {
    private Long hobosEatingTime;
    private Long hobosStealingTime;
    private int hobosCount;
    List<Hobos> hobosList;
    Map<String, Long> ingredientsCount;
    ConcurrentHashMap<String, Long> nowIngredientsCount;

    public class Hobos implements Runnable {
        private boolean isStealing = true;

        public void makeCooking() {
            this.isStealing = false;
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
                            Thread.sleep(hobosStealingTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Hobo steeled product " + ingredientToSteeling + " from dock");

                        Long newCount = nowIngredientsCount.get(ingredientToSteeling) + 1;
                        nowIngredientsCount.put(ingredientToSteeling, newCount);
                        System.out.println("Hobo drought "+ ingredientToSteeling);

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

    public GroupOfHobos setHobosStealingTime(Long hobosStealingTime) {
        this.hobosStealingTime = hobosStealingTime;
        return this;
    }

    public GroupOfHobos setHobosEatingTime(Long hobosEatingTime) {
        this.hobosEatingTime = hobosEatingTime;
        return this;
    }

    public GroupOfHobos setHobosCount(int hobosCount) {
        this.hobosCount = hobosCount;
        return this;
    }

    public GroupOfHobos setIngredientsCount(Map<String, Long> ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
        return this;
    }

    int getHobosCount() {
        return this.hobosCount;
    }

    public void generateHobos() {
        hobosList = new ArrayList<>();
        for (int i = 0; i < hobosCount; ++i) {
            hobosList.add(new Hobos());
        }
        int cooker1 = ThreadLocalRandom.current().nextInt(0, hobosCount);
        hobosList.get(cooker1).makeCooking();
        int tmp = ThreadLocalRandom.current().nextInt(1, hobosCount - 1);
        hobosList.get((cooker1 + tmp) % hobosCount).makeCooking();//TODO exception if already cooker

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
                Thread.sleep(hobosEatingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
