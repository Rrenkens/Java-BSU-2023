package by.nrydo.docks_and_hobos;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Hobo implements Runnable {
    private boolean isCook = false;
    private Semaphore cookSemaphore;
    private Semaphore eatingSemaphore;
    private Lock ingredientsLock;
    private final CyclicBarrier eatingBarrier;
    private final Dock dock;
    private final HoboGroup group;

    public Hobo(HoboGroup group, Dock dock, CyclicBarrier eatingBarrier) {
        this.dock = dock;
        this.group = group;
        this.eatingBarrier = eatingBarrier;
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
            e.printStackTrace();
        }
    }

    private void stealIngredient() {
        int ingredient = dock.steal();
        System.out.println("Putting " + this.hashCode());
        group.putIngredient(ingredient);
        System.out.println("Stealing " + this.hashCode() + "\n" + Arrays.toString(group.getIngredients()));
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
