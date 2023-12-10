package by.Bvr_Julia.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hobo implements Runnable {
    private Dock dock;
    public boolean needToSteal;
    private Thread thread;
    private volatile Map<String, Long> ingridients;
    private final List<String> cargo_types;
    private final Long stealing_time;

    public synchronized Long Steal(String type) {
        System.out.println("Hobo is trying to steal cargo. " + type);
        return dock.getSomeCargo((long) 1, type);
    }

    Hobo(Dock dock, int num, Map<String, Long> ingridients, List<String> cargo_types, Long stealing_time) {
        this.dock = dock;
        this.ingridients = ingridients;
        this.cargo_types = cargo_types;
        needToSteal = false;
        this.stealing_time=stealing_time;
        thread = new Thread(this, "Hobo " + Integer.toString((num)));
        System.out.println("Hobo was made up. "+ Integer.toString((num)));
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) / 1000 < Main.time) {
            if (needToSteal) {
                try {
                    Long cargo = Randomizer.generate((long) 0, (long) cargo_types.size() - 1);
                    Long tmp = Steal(cargo_types.get(cargo.intValue()));
                    tmp += ingridients.get(cargo_types.get(cargo.intValue()));
                    ingridients.remove(cargo_types.get(cargo.intValue()));
                    ingridients.put(cargo_types.get(cargo.intValue()), tmp);
                    Thread.sleep(stealing_time.intValue()*1000);
                } catch (InterruptedException e) {
                    System.out.println("Hobo has been interrupted\n");
                }
            }
        }
        thread.interrupt();
    }

    public Thread getThread(){
        return  thread;
    }

}
