package by.Lenson423.docks_and_hobos.main_actors;

import by.Lenson423.docks_and_hobos.utilities.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.INFO;

public class HobosGroup implements Runnable {
    final AtomicIntegerArray currentCount;
    final int[] ingredientsCount;
    final int eatingTime;
    final List<Hobo> hobos;

    private static final Logger logger = Logger.getLogger(HobosGroup.class.toString());

    public HobosGroup(int @NotNull [] ingredientsCount, int eatingTime, int @NotNull [] hobosStealingTimes) {
        for (var count : ingredientsCount) {
            if (count < 0){
                throw new IllegalArgumentException("Invalid ingredient count");
            }
        }
        this.ingredientsCount = ingredientsCount;
        this.currentCount = new AtomicIntegerArray(ingredientsCount.length);

        if (eatingTime <= 0){
            throw new IllegalArgumentException("Eating time is invalid");
        }
        this.eatingTime = eatingTime;

        int k = hobosStealingTimes.length;
        if (k < 3) {
            throw new IllegalArgumentException("Invalid number of hobos");
        }
        hobos = new ArrayList<>(k);
        for (int i = 0; i < k; ++i) {
            hobos.add(i, new Hobo(hobosStealingTimes[i], i));
        }
    }

    int indexToSteal() {
        for (int i = 0; i < ingredientsCount.length; ++i) {
            if ((currentCount.get(i) - ingredientsCount[i]) < 0) {
                return i;
            }
        }
        return -1;
    }

    public void run() {
        logger.log(INFO, "Hobos group start steeling");
        while (true) {
            Collections.shuffle(hobos); //Hobos at indexes 0, 1 cook
            logger.log(CONFIG, "Hobos with id" + hobos.get(0).hoboId +
                    ", " + hobos.get(1).hoboId + " are cookers");

            int count = hobos.size();
            Thread[] hoboThreads = new Thread[count - 2];
            for (int i = 2; i < count; ++i) {
                hoboThreads[i - 2] = new Thread(hobos.get(i));
            }
            for (Thread thread : hoboThreads) {
                thread.start();
            }

            for (Thread thread : hoboThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.log(CONFIG, "All hobos come");
            for (int i = 0; i < ingredientsCount.length; ++i) {
                currentCount.addAndGet(i, -ingredientsCount[i]);
            }

            try {
                logger.log(INFO, "Hobos group start eating");
                Thread.sleep(eatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    private class Hobo implements Runnable {
        final int stealingTime;
        final int hoboId;

        public Hobo(int stealingTime, int hoboId) {
            if (stealingTime <= 0) {
                throw new IllegalArgumentException("Stealing time less or equal then 0");
            }
            this.stealingTime = stealingTime;
            if (hoboId < 0) {
                throw new IllegalArgumentException("Index is negative");
            }
            this.hoboId = hoboId;
        }

        @Override
        public void run() {
            logger.log(CONFIG, "Hobo with id " + hoboId + " start steeling");
            for (int index = indexToSteal(); index != -1; index = indexToSteal()) {
                boolean flag = false;
                while (true) {
                    for (Dock dock : Controller.getController().getModel().getDocks()) {
                        if (dock.stealProduct(index)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            Thread.sleep(stealingTime * 1000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        logger.log(CONFIG, "Hobo with id " + hoboId
                                + " steel product with id " + index + " from dock");
                        currentCount.incrementAndGet(index);
                        break;
                    }
                }
            }
        }

    }
}
