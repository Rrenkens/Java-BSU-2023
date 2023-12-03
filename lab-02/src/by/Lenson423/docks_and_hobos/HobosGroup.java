package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class HobosGroup implements Runnable{
    final AtomicIntegerArray currentCount;
    final int[] ingredientsCount;
    final int eatingTime;
    final List<Hobo> hobos;

    public HobosGroup(int @NotNull[] ingredientsCount, int eatingTime, int@NotNull[] hobosStealingTimes) {
        this.ingredientsCount = ingredientsCount;
        this.currentCount = new AtomicIntegerArray(ingredientsCount.length);
        this.eatingTime = eatingTime;

        int k = hobosStealingTimes.length;
        if (k < 3){
            throw new IllegalArgumentException("Invalid number of hobos");
        }
        hobos = new ArrayList<>(k);
        for (int i = 0; i < k; ++i){
            hobos.add(i, new Hobo(hobosStealingTimes[i], i));
        }
    }

    int indexToSteal(){
        for (int i = 0; i < ingredientsCount.length; ++i){
            if ((currentCount.get(i) - ingredientsCount[i]) < 0){
                return i;
            }
        }
        return -1;
    }

    public void run(){
        while(true){
            Collections.shuffle(hobos); //Hobos at indexes 0, 1 cook

            int count = hobos.size();
            Thread[] hoboThreads = new Thread[count - 2];
            for(int i = 2; i < count; ++i){
                hoboThreads[i - 2] = new Thread(hobos.get(i));
            }
            for (Thread thread: hoboThreads){
                thread.start();
            }

            for (Thread thread: hoboThreads){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            for (int i = 0; i < ingredientsCount.length; ++i) {
                currentCount.addAndGet(i, -ingredientsCount[i]);
            }

            try {
                Thread.sleep(eatingTime * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Hobo implements Runnable{
        final int stealingTime;
        final int hoboId;

        public Hobo(int stealingTime, int hoboId) {
            this.stealingTime = stealingTime;
            this.hoboId = hoboId;
        }

        @Override
        public void run() {
            for (int index = indexToSteal(); index != -1;) {
                //Dock dockToSteal;
                boolean flag = false;
                while(true) {
                    for (Dock dock : Controller.getController().getModel().getDocks()) {
                        if (dock.stealProduct(index)) {
                            //dockToSteal = dock;
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
                        currentCount.incrementAndGet(index);
                        break;
                    }
                }
            }
        }

    }
}
