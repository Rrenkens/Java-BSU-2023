package by.nrydo.docks_and_hobos;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HoboGroup implements Runnable {
    private final Thread[] threads;
    private final int[] ingredients;
    private final Semaphore cookSemaphore;
    private final Semaphore eatingSemaphore;
    private final Lock ingredientsLock;
    private final CyclicBarrier eatingBarrier;
    private final AtomicBoolean eating;
    private final Logger logger;

    HoboGroup(Dock dock) {
        Hobo[] hobos = new Hobo[ConfigReader.getInstance().getHobos()];
        ingredients = new int[ConfigReader.getInstance().getIngredientsCount().length];

        cookSemaphore = new Semaphore(0);
        eatingSemaphore = new Semaphore(0);
        ingredientsLock = new ReentrantLock();
        eatingBarrier = new CyclicBarrier(hobos.length + 1, this::eat);
        eating = new AtomicBoolean(false);

        threads = new Thread[hobos.length];
        logger = createLogger();

        for (int i = 0; i < hobos.length; i++) {
            hobos[i] = new Hobo(this, dock, eatingBarrier);
            threads[i] = new Thread(hobos[i]);
        }

        Random random = new Random();

        int firstCook = random.nextInt(hobos.length);
        int secondCook = (firstCook + random.nextInt(hobos.length - 1) + 1) % hobos.length;

        hobos[firstCook].becomeCook(cookSemaphore, ingredientsLock, eatingSemaphore);
        hobos[secondCook].becomeCook(cookSemaphore, ingredientsLock, eatingSemaphore);
    }

    private Logger createLogger() {
        Logger logger = Logger.getLogger(HoboGroup.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("log/hobo_group.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create logger", e);
        }
        return logger;
    }

    private void logEating() {
        logger.info("Hobos are eating");
    }

    void eat() {
        logEating();
        int[] ingredientsCount = ConfigReader.getInstance().getIngredientsCount();
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] -= ingredientsCount[i];
        }
        eating.set(false);
    }

    public void putIngredient(int ingredient) {
        ingredientsLock.lock();
        ingredients[ingredient]++;
        cookSemaphore.release();
        ingredientsLock.unlock();
    }

    public int[] getIngredients() {
        return ingredients;
    }

    public AtomicBoolean isEating() {
        return eating;
    }

    @Override
    public void run() {
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            while (true) {
                eatingSemaphore.acquire();
                cookSemaphore.release();
                eatingBarrier.await();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            logger.log(Level.SEVERE, "Thread interrupted", e);
        }
    }
}