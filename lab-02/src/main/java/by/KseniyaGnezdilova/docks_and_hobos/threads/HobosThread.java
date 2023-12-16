package by.KseniyaGnezdilova.docks_and_hobos.threads;

import by.KseniyaGnezdilova.docks_and_hobos.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class HobosThread extends Thread{
    private final int hobos_num;
    private final int stealing_time;
    private final int eating_time;
    private final int ingridients_size;
    private final HashMap<String, Integer> ingridients_count;

    public HobosThread(int hobos_num, int stealing_time, int eating_time, HashMap<String, Integer> ingridients_count){
        this.eating_time = eating_time;
        this.hobos_num = hobos_num;
        this.stealing_time = stealing_time;
        this.ingridients_size = ingridients_count.size();
        this.ingridients_count = ingridients_count;

    }

    @Override
    public void run(){
        while (true){
            try {
                Main.sem.acquire(Main.sem.availablePermits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int ingridient = 0;
            for (var key: ingridients_count.keySet()){
                if (ingridients_count.get(key) <= Main.ingridients.get(key)){
                    ingridient++;
                }
            }
            if (ingridient != ingridients_size){
                int hobos_free = hobos_num - 2;
                int i = 0;
                var names =  Main.dock.keySet().toArray();
                while (hobos_free > 0 && i < names.length){
                    String key = (String) names[i];
                    if (Main.dock.get(key) > 0 && Main.ingridients.get(key) < ingridients_count.get(key)){
                        Main.dock.replace(key, Main.dock.get(key) - 1);
                        hobos_free--;
                        Main.ingridients.replace(key, Main.ingridients.get(key) + 1);
                    }
                    i++;
                }

                try {
                    Main.sem.release();
                    sleep(stealing_time * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else{
                for (var key: Main.ingridients.keySet()){
                    Main.ingridients.replace(key, Main.ingridients.get(key) - ingridients_count.get(key));
                }
                try {
                    Main.sem.release();
                    sleep(eating_time * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Main.sem.release();
                sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
