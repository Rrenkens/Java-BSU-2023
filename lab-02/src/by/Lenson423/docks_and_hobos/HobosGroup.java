package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
            throw new IllegalArgumentException("Invalis number of hobos");
        }
        hobos = new ArrayList<>(k);
        for (int i = 0; i < k; ++i){
            hobos.add(i, new Hobo(hobosStealingTimes[i], i));
        }
    }

    public void run(){
        while(true){
            //ToDo
        }
    }

    private class Hobo{
        final int stealingTime;
        final int hoboId;

        public Hobo(int stealingTime, int hoboId) {
            this.stealingTime = stealingTime;
            this.hoboId = hoboId;
        }


        public void run() {
            while (true) {
                //ToDo
            }
        }

        private void steal(@NotNull String productName) throws InterruptedException {
            Dock dockToSteal = null;
            boolean flag = false;
            do {
                for (Dock dock : Controller.getController().getModel().getDocks()) {
                    if (dock.stealProduct(productName)) {
                        dockToSteal = dock;
                        flag = true;
                        break;
                    }
                }
            } while (!flag);

            Thread.sleep(stealingTime * 1000L);
            currentCount.incrementAndGet(Controller.getController().getModel().getCargoTypes().getByName(productName));
        }
    }
}
