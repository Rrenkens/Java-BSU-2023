package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class HobosManager implements Runnable{

    private ArrayList<Hobo> hobos;

    private int eatingTime;

    private ArrayList<Hobo> workingHobos;

//    private ArrayList<Hobo> cookingHobos;


    private final AtomicIntegerArray ingredients;

    public HobosManager(ArrayList<Hobo> hobos, int eatingTime,
                        AtomicIntegerArray ingredients) {
        this.hobos = hobos;
        this.eatingTime = eatingTime;
        this.ingredients = ingredients;

        //this.cookingHobos = new ArrayList<>();

    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Hobos are starting stealing");
            AtomicIntegerArray necessaryIngredients = new AtomicIntegerArray(ingredients.length());
            for (int i = 0; i < necessaryIngredients.length(); ++i) {
                necessaryIngredients.set(i, ingredients.get(i));
            }
            for (Hobo hobo: hobos) {
                hobo.setNecessaryIngredients(necessaryIngredients);
            }


            this.distributeResponsibilities();
            this.createStrategy();

            int count1 = 0;
            int count2 = 0;
            for (Hobo hobo: hobos) {
                if(hobo.getCookingState() == true) {
                    count1++;
                } else {
                    count2++;
                }
            }
            System.out.println("Count1 = " + count1);
            System.out.println("Count2 = " + count2);
            ArrayList<Thread> threads = new ArrayList<>();

//            int size = workingHobos.size();
//            for (int i = 0; i < size; ++i) {
//                threads.add(new Thread(workingHobos.get(i)));
//                threads.get(i).start();
//            }
//            for (int i = 0; i < size; ++i) {
//                try {
//                    threads.get(i).join();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }

            int size = hobos.size();
            for (int i = 0; i < size; ++i) {
                threads.add(new Thread(hobos.get(i)));
                threads.get(i).start();
            }
            for (int i = 0; i < size; ++i) {
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Hobos are about to eat");
            try {
                Thread.sleep(eatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }

    private void distributeResponsibilities() {
        for (Hobo hobo: this.hobos) {
            hobo.setCookingState(false);
        }

        workingHobos = new ArrayList<>(hobos);

        Random random = new Random();
        for (int i = 0; i < 2; ++i) {
            int randIndex = random.nextInt(workingHobos.size());
            workingHobos.get(randIndex).setCookingState(true);
            workingHobos.remove(randIndex);
        }

    }

//    private void createListOfWorkingHobos() {
//        workingHobos = new ArrayList<>(hobos);
//        Random random = new Random();
//        for (int i = 0; i < 2; ++i) {
//            int randIndex = random.nextInt(workingHobos.size());
//            workingHobos.remove(randIndex);
//        }
//    }

    private void createStrategy() {
        int workingHobosNumber = this.workingHobos.size();
        int ingredientsNumber = this.ingredients.length();

        if (workingHobosNumber > ingredientsNumber) {
            int cyclesCount = workingHobosNumber / ingredientsNumber;
            for (int i = 0; i < cyclesCount; ++i) {
                for (int j = 0; j < ingredientsNumber; ++j) {
                    this.workingHobos.get(i * ingredientsNumber + j).addIngredient(j);
                }
            }

            int freeHobosLeft = workingHobosNumber - cyclesCount * ingredientsNumber;
            int startIndex = cyclesCount * ingredientsNumber;
            for (int i = 0; i < freeHobosLeft; ++i) {
                this.workingHobos.get(startIndex + i).addIngredient(i);
            }
            return;
        }

        int cyclesCount = ingredientsNumber / workingHobosNumber;
        for (int i = 0; i < cyclesCount; ++i) {
            for (int j = 0; j < workingHobosNumber; ++j) {
                this.workingHobos.get(j).addIngredient(i * workingHobosNumber + j);
            }
        }

        int ingredientLeft = ingredientsNumber - cyclesCount * workingHobosNumber;
        int startIndex = cyclesCount * workingHobosNumber;
        for (int i = 0; i < ingredientLeft; ++i) {
            this.workingHobos.get(i).addIngredient(startIndex + i);
        }
    }
}



/*
package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class HobosManager implements Runnable{

    private ArrayList<Hobo> hobos;

    private int eatingTime;

    private ArrayList<Hobo> workingHobos;


    private final AtomicIntegerArray ingredients;

    public HobosManager(ArrayList<Hobo> hobos, int eatingTime,
                        AtomicIntegerArray ingredients) {
        this.hobos = hobos;
        this.eatingTime = eatingTime;
        this.ingredients = ingredients;

    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Hobos are starting stealing");
            AtomicIntegerArray necessaryIngredients = new AtomicIntegerArray(ingredients.length());
            for (int i = 0; i < necessaryIngredients.length(); ++i) {
                necessaryIngredients.set(i, ingredients.get(i));
            }
            for (Hobo hobo: hobos) {
                hobo.setNecessaryIngredients(necessaryIngredients);
            }


            this.createListOfWorkingHobos();
            this.createStrategy();
            ArrayList<Thread> threads = new ArrayList<>();
            int size = workingHobos.size();
            for (int i = 0; i < size; ++i) {
                threads.add(new Thread(workingHobos.get(i)));
                threads.get(i).start();
            }
            for (int i = 0; i < size; ++i) {
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Hobos are about to eat");
            try {
                Thread.sleep(eatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }

    private void createListOfWorkingHobos() {
        for (Hobo hobo: this.hobos) {
            hobo.setCookingState(false);
        }
        Random random = new Random();
        for (int i = 0; i < 2; ++i) {
            int randIndex = random.nextInt(workingHobos.size());
            workingHobos.remove(randIndex);
        }
    }

//    private void createListOfWorkingHobos() {
//        workingHobos = new ArrayList<>(hobos);
//        Random random = new Random();
//        for (int i = 0; i < 2; ++i) {
//            int randIndex = random.nextInt(workingHobos.size());
//            workingHobos.remove(randIndex);
//        }
//    }

    private void createStrategy() {
        int workingHobosNumber = this.workingHobos.size();
        int ingredientsNumber = this.ingredients.length();

        if (workingHobosNumber > ingredientsNumber) {
            int cyclesCount = workingHobosNumber / ingredientsNumber;
            for (int i = 0; i < cyclesCount; ++i) {
                for (int j = 0; j < ingredientsNumber; ++j) {
                    this.workingHobos.get(i * ingredientsNumber + j).addIngredient(j);
                }
            }

            int freeHobosLeft = workingHobosNumber - cyclesCount * ingredientsNumber;
            int startIndex = cyclesCount * ingredientsNumber;
            for (int i = 0; i < freeHobosLeft; ++i) {
                this.workingHobos.get(startIndex + i).addIngredient(i);
            }
            return;
        }

        int cyclesCount = ingredientsNumber / workingHobosNumber;
        for (int i = 0; i < cyclesCount; ++i) {
            for (int j = 0; j < workingHobosNumber; ++j) {
                this.workingHobos.get(j).addIngredient(i * workingHobosNumber + j);
            }
        }

        int ingredientLeft = ingredientsNumber - cyclesCount * workingHobosNumber;
        int startIndex = cyclesCount * workingHobosNumber;
        for (int i = 0; i < ingredientLeft; ++i) {
            this.workingHobos.get(i).addIngredient(startIndex + i);
        }
    }
}
*/
