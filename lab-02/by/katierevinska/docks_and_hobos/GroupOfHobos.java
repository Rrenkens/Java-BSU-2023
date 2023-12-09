package by.katierevinska.docks_and_hobos;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GroupOfHobos implements Runnable {
    private Long hobosEatingTime;
    private Long hobosStealingTime;
    private int hobosCount;
    List<Hobos> hobosList;
    Process process;
    List<String> cargoTypes;
    Map<String, Long> ingredientsCount;
    Map<String, Long> nowIngredientsCount;

    public class Hobos implements Runnable {
        private boolean isStealing = true;

        public void makeCooking() {
            this.isStealing = false;
        }

        Optional<String> ingredientNeedToSteeling() {
            for (int i = 0; i < cargoTypes.size(); ++i) {
                if (nowIngredientsCount.get(cargoTypes.get(i)) < ingredientsCount.get(cargoTypes.get(i))) {
                    return Optional.of(cargoTypes.get(i));
                }
            }
            return Optional.empty();
        }

        public void run() {
            while (ingredientNeedToSteeling().isPresent()) {
                boolean flag = false;
                while (true) {
                    for (var dock : process.docks) {
                        if (dock.steelIngredient(ingredientNeedToSteeling().get())) {
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
                        System.out.println("Hobo steeled product " + ingredientNeedToSteeling().isPresent() + " from dock");

                        Long newCount = nowIngredientsCount.get(ingredientNeedToSteeling().get()) + 1;
                        nowIngredientsCount.put(ingredientNeedToSteeling().get(), newCount);
                        System.out.println("hobos get product " + ingredientNeedToSteeling().isPresent());

                        break;
                    }
                }
            }
        }
    }

    GroupOfHobos(Process process) {
        this.process = process;
        this.nowIngredientsCount = new HashMap<>();
        for (var ing : process.shipGenerator.cargoTypes) {
            this.nowIngredientsCount.put(ing, 0L);
        }
    }

    GroupOfHobos setHobosStealingTime(Long hobosStealingTime) {
        this.hobosStealingTime = hobosStealingTime;
        return this;
    }

    GroupOfHobos setHobosEatingTime(Long hobosEatingTime) {
        this.hobosEatingTime = hobosEatingTime;
        return this;
    }

    GroupOfHobos setHobosCount(int hobosCount) {
        this.hobosCount = hobosCount;
        return this;
    }

    GroupOfHobos setCargoTypes(List<String> cargoTypes) {
        this.cargoTypes = cargoTypes;
        return this;
    }

    GroupOfHobos setIngredientsCount(Map<String, Long> ingredientsCount) {
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
            System.out.println("hobos steeled all they need");
            for (String cargoType : cargoTypes) {
                nowIngredientsCount.put(cargoType, 0L);
            }

            try {
                System.out.println("hobos eating)))");
                Thread.sleep(hobosEatingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
