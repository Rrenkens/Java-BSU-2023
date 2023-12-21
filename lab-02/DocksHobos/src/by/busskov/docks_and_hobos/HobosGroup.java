package by.busskov.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

public class HobosGroup implements Runnable {
    private final Lock lock = new ReentrantLock();
    private final Condition startWork = lock.newCondition();
    private final Condition startEating = lock.newCondition();
    private final Condition stopEating = lock.newCondition();
    private final Condition getIngredient = lock.newCondition();
    private final Random random;
    private final ArrayList<Hobo> hobos;
    private final int eatingTime;
    private final int stealingTime;
    private final HashMap<String, Integer> necessaryIngredients;
    private final HashMap<String, Integer> currentIngredients;
    private final Dock.Warehouse warehouse;
    private final BayLogger logger;
    public static enum Task {
        STOP,
        COOK,
        STEAL,
        EAT
    }
    public HobosGroup(
            int numberOfHobos,
            int eatingTime,
            int stealingTime,
            HashMap<String, Integer> necessaryIngredients,
            Dock.Warehouse warehouse,
            BayLogger logger
    ) {
        if (numberOfHobos <= 2) {
            throw new IllegalArgumentException("Number of hobos must be > 2");
        }
        this.eatingTime = eatingTime * 1000; // to make milliseconds
        this.stealingTime = stealingTime * 1000;
        this.necessaryIngredients = new HashMap<>(necessaryIngredients);
        this.random = new Random();

        this.hobos = new ArrayList<>(numberOfHobos);
        for (int i = 0; i < numberOfHobos; ++i) {
            hobos.add(new Hobo(i + 1));
        }

        this.currentIngredients = new HashMap<>(necessaryIngredients.size());
        this.warehouse = warehouse;
        this.logger = logger;
    }

    @Override
    public void run() {
        ArrayList<Thread> hobosThreads = new ArrayList<>(hobos.size());
        for (Hobo hobo : hobos) {
            hobosThreads.add(new Thread(hobo));
        }
        for (Thread hoboThread : hobosThreads) {
            hoboThread.start();
        }
        while(true) {
            for (String key : necessaryIngredients.keySet()) {
                currentIngredients.put(key, 0);
            }

            int firstCook = random.nextInt(hobos.size());
            int secondCook = random.nextInt(hobos.size() - 1);
            if (secondCook == firstCook) {
                ++secondCook;
            }
            hobos.get(firstCook).setTask(Task.COOK);
            hobos.get(secondCook).setTask(Task.COOK);
            for (int i = 0; i < hobos.size(); ++i) {
                if (i != firstCook && i != secondCook) {
                    hobos.get(i).setTask(Task.STEAL);
                }
            }

            lock.lock();
            startWork.signal();
            lock.unlock();

            logger.log(Level.INFO, "Hobos start working");

            //Wait for enough ingredients
            boolean enoughIngredients = false;
            while (!enoughIngredients) {
                lock.lock();
                try {
                    getIngredient.await();
                } catch (InterruptedException ignored) {}
                finally {
                    lock.unlock();
                }
                enoughIngredients = true;
                for (String key : currentIngredients.keySet()) {
                    if (currentIngredients.get(key) < necessaryIngredients.get(key)) {
                        enoughIngredients = false;
                    }
                }
            }
            for (Hobo hobo : hobos) {
                hobo.setTask(Task.EAT);
            }

            lock.lock();
            startEating.signal();
            lock.unlock();

            logger.log(Level.INFO, "Hobos start eating");
            for (String key : necessaryIngredients.keySet()) {
                currentIngredients.put(key, 0);
            }

            try {
                Thread.sleep(eatingTime);
            } catch (InterruptedException ignored) {}
            for (Hobo hobo : hobos) {
                hobo.setTask(Task.STOP);
            }

            lock.lock();
            stopEating.signal();
            lock.unlock();
        }
    }

    private class Hobo implements Runnable {
        private HobosGroup.Task currentTask = HobosGroup.Task.STOP;
        private final int name;
        public Hobo(int name) {
            this.name = name;
        }
        @Override
        public void run() {
            while (true) {
                stop();
                work();
                eat();
            }
        }

        public void stop() {
            while(!currentTask.equals(Task.STEAL) && !currentTask.equals(Task.COOK)) {
                lock.lock();
                try {
                    startWork.await();
                } catch (InterruptedException ignored) {}
                finally {
                    lock.unlock();
                }
            }
        }

        public void work() {
            if (currentTask.equals(HobosGroup.Task.COOK)) {
                cook();
            } else if (currentTask.equals(HobosGroup.Task.STEAL)) {
                steal();
            }
        }

        private void cook() {
            logger.log(Level.ALL, "Hobo{0} cooking", name);
            while(!currentTask.equals(Task.EAT)) {
                lock.lock();
                try {
                    startEating.await();
                } catch (InterruptedException ignored) {}
                finally {
                    lock.unlock();
                }
            }
        }

        private void steal() {
            logger.log(Level.ALL, "Hobo{0} stealing", name);
            ArrayList<String> ingredients = new ArrayList<>(necessaryIngredients.keySet());
            int i = 0;
            while(!currentTask.equals(Task.EAT)) {
                try {
                    Thread.sleep(stealingTime);
                } catch (InterruptedException ignored) {}

                if (ingredients.isEmpty()) {

                    continue;
                }

                String ingredient = ingredients.get(i);
                logger.log(Level.ALL, "Hobo{0} trying to steal {1}", new Object[]{name, ingredient});
                if (warehouse.steal(ingredient)) {
                    synchronized (currentIngredients) {
                        int number = currentIngredients.get(ingredient);
                        ++number;
                        currentIngredients.put(ingredient, number);
                        logger.log(
                                Level.ALL,
                                "Hobo{0} stole {1}, current ingredients: {2}",
                                new Object[]{name, ingredient, currentIngredients});
                    }

                    lock.lock();
                    getIngredient.signal();
                    lock.unlock();

                    if (currentIngredients.get(ingredient) >= necessaryIngredients.get(ingredient)) {
                        ingredients.remove(i);
                        i = 0;
                    }
                } else {
                    logger.log(Level.ALL, "Hobo{0} cant steal {1}", new Object[]{name, ingredient});
                    i = (i + 1) % ingredients.size();
                }
            }
        }

        public void eat() {
            while(!currentTask.equals(Task.STOP)) {
                lock.lock();
                try {
                    stopEating.await();
                } catch (InterruptedException ignored) {}
                finally {
                    lock.unlock();
                }
            }
        }

        public void setTask(HobosGroup.Task task) {
            currentTask = task;
        }
    }
}
