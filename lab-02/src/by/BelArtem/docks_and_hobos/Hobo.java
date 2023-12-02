package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Hobo implements Runnable{

    private final List<Dock> dockList;

    private final ArrayList<Integer> indicesOfIngredientsToSteal = new ArrayList<>();

    private AtomicIntegerArray necessaryIngredients;

    private final int stealingTime;



    public Hobo(List<Dock> docks, AtomicIntegerArray necessaryIngredients, int stealingTime) {
        this.dockList = docks;
        this.necessaryIngredients = necessaryIngredients;
        this.stealingTime = stealingTime;
    }


    public void addIngredient(int index) {
        indicesOfIngredientsToSteal.add(index);
    }

    public void setNecessaryIngredients (AtomicIntegerArray necessaryIngredients) {
        this.necessaryIngredients = necessaryIngredients;
    }

    @Override
    public void run() {
        for (int ingredientIndex: indicesOfIngredientsToSteal) {
            steelIngredient(ingredientIndex);
        }
    }

    private void steelIngredient(int index) {
        Random random = new Random();
        while (necessaryIngredients.get(index) > 0) {
            int randomDockIndex = random.nextInt(dockList.size());
            AtomicIntegerArray currentStock = dockList.get(randomDockIndex).getStock();
            while (necessaryIngredients.get(index) > 0 && currentStock.get(index) > 0){
                int itemsLeft = currentStock.decrementAndGet(index);
                if (itemsLeft < 0){
                    currentStock.incrementAndGet(index);
                    break;
                }

                try {
                    Thread.sleep(this.stealingTime * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Has stolen a " + index + " item");

                necessaryIngredients.set(index, Math.max( 0, necessaryIngredients.get(index) - 1));
            }
        }
    }


}
