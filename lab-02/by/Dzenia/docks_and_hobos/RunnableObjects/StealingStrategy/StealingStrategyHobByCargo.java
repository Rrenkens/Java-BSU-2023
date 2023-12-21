package by.Dzenia.docks_and_hobos.RunnableObjects.StealingStrategy;

import by.Dzenia.docks_and_hobos.RunnableObjects.Hobos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class StealingStrategyHobByCargo implements StealingStrategy {

    @Override
    public void goStealing(Hobos hobos) {
        List<Hobos.Hobo> hoboList = hobos.getHobos();
        List<String> ingredientsToSteal = new ArrayList<>(hobos.getIngredientsCount().keySet());
        Collections.shuffle(hoboList);
        int count = 0;
        while (count < ingredientsToSteal.size()) {
            ArrayList<Thread> hoboThreads = new ArrayList<>();
            for (int i = 2; i < hoboList.size(); ++i) {
                hoboList.get(i).setCargoToSteal(ingredientsToSteal.get(count));
                count++;
                hoboThreads.add(new Thread(hobos.getHobos().get(i)));
                if (count >= ingredientsToSteal.size()) {
                    break;
                }
            }
            for (Thread thread: hoboThreads) {
                thread.start();
            }
            for (Thread thread: hoboThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
