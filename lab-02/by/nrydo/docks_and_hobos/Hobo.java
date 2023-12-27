package by.nrydo.docks_and_hobos;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Hobo implements Runnable {
    private boolean isCook = false;
    private Semaphore cookSemaphore;
    private Semaphore eatingSemaphore;
    private Lock ingredientsLock;
    private final CyclicBarrier eatingBarrier;
    private final Dock dock;
    private final HoboGroup group;
    private final Logger logger;

    public Hobo(HoboGroup group, Dock dock, CyclicBarrier eatingBarrier) {
        this.dock = dock;
        this.group = group;
        this.eatingBarrier = eatingBarrier;
        this.logger = createLogger();
    }

    private Logger createLogger() {
        Logger logger = Logger.getLogger(Hobo.class.getName());
        try {
            // Создание обработчика файла логов
            FileHandler fileHandler = new FileHandler("log/hobo.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create logger", e);
        }
        return logger;
    }

    public void becomeCook(Semaphore cookSemaphore, Lock ingredientsLock, Semaphore eatingSemaphore) {
        isCook = true;
        this.cookSemaphore = cookSemaphore;
        this.ingredientsLock = ingredientsLock;
        this.eatingSemaphore = eatingSemaphore;
    }

    @Override
    public void run() {
        int stealingTime = ConfigReader.getInstance().getStealingTime();
        try {
            if (isCook) {
                while (true) {
                    tryCook();
                }
            }
            while (true) {
                Thread.sleep(stealingTime * 1000L);
                while (group.isEating().get()) {
                    eatingBarrier.await();
                    Thread.sleep(ConfigReader.getInstance().getEatingTime() * 1000L);
                }
                stealIngredient();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            logger.log(Level.SEVERE, "Thread interrupted", e);
        }
    }

    private void stealIngredient() {
        int ingredient = dock.steal();
        group.putIngredient(ingredient);
        logger.info("Stole ingredient: " + ConfigReader.getInstance().getCargoTypes()[ingredient]);
    }

    private void tryCook() {
        try {
            cookSemaphore.acquire();
            ingredientsLock.lock();
            if (group.isEating().get()) {
                ingredientsLock.unlock();
                eatingBarrier.await();
                Thread.sleep(ConfigReader.getInstance().getEatingTime() * 1000L);
                return;
            }
            int[] ingredients = group.getIngredients();
            int[] ingredientsCount = ConfigReader.getInstance().getIngredientsCount();
            for (int i = 0; i < ingredients.length; i++) {
                if (ingredients[i] < ingredientsCount[i])  {
                    ingredientsLock.unlock();
                    return;
                }
            }
            eatingSemaphore.release();
            group.isEating().set(true);
            ingredientsLock.unlock();
            eatingBarrier.await();
            Thread.sleep(ConfigReader.getInstance().getEatingTime() * 1000L);
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
