package by.Bvr_Julia.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HobosBase implements  Runnable {
    private List<Hobo> hobo;
    private Thread thread;
    final private Map<String, Long> ingridients_count;
    final private Long stealing_time;
    final private Long eating_time;
    private Map<String, Long> ingridients;

    public Thread getThread() {
        return thread;
    }

    HobosBase(Map<String, Long> ingridients, List<Hobo> hobo, Map<String, Long> ingridients_count, Long stealing_time, Long eating_time) {
        this.eating_time = eating_time;
        this.stealing_time = stealing_time;
        this.ingridients_count = ingridients_count;
        this.ingridients = ingridients;
        this.hobo = hobo;
        thread = new Thread(this, "HobosBase thread");
        System.out.println("HobosBase was made up. " + ingridients_count);
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) / 1000 < Main.time) {

            try {
                for (int i = 2; i < hobo.size(); i++) {
                    hobo.get(i).needToSteal = true;
                    /*Long cargo = Randomizer.generate((long) 0, (long) cargo_types.size() - 1);
                    Long tmp = hobo.get(i).Steal(cargo_types.get(cargo.intValue()));
                    tmp += ingridients.get(cargo_types.get(cargo.intValue()));
                    ingridients.remove(cargo_types.get(cargo.intValue()));
                    ingridients.put(cargo_types.get(cargo.intValue()), tmp);
                    // when hobos don't have threads
                    */
                    /*for (String cargo : ingridients.keySet()){
                        if (ingridients.get(cargo).compareTo(ingridients_count.get(cargo))<0){
                            Long tmp = hobo.get(i).Steal(cargo);
                            tmp += ingridients.get(cargo);
                            ingridients.remove(cargo);
                            ingridients.put(cargo, tmp);
                            break;
                        }
                    }
                    // when hobos try to steal not random cargo, but that one that they need
                    */
                }
                Thread.sleep(stealing_time.intValue() * 1000);
                for (int i = 2; i < hobo.size(); i++) {
                    hobo.get(i).needToSteal = false;
                }
                boolean check = true;
                for (String cargo : ingridients.keySet()) {
                    if (ingridients.get(cargo).compareTo(ingridients_count.get(cargo)) < 0) {
                        check = false;
                        break;
                    }
                }
                System.out.println("Hobos have " + ingridients);
                if (check) {
                    System.out.println("Hobos are eating!\n");
                    for (String cargo : ingridients_count.keySet()) {
                        Long tmp = ingridients.get(cargo);
                        ingridients.remove(cargo);
                        ingridients.put(cargo, tmp - ingridients_count.get(cargo));
                    }
                    System.out.println("Hobos have " + ingridients);
                    Thread.sleep(eating_time.intValue() * 1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Hobos base has been interrupted\n");
            }
        }

        thread.interrupt();

    }

}
