package by.AlexHanimar.docs_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.INFO;

public class HoboGroupManager implements Runnable {
    private final HoboGroup hoboGroup;
    private final Dock dock;
    private final int eatingTime;
    private static final Logger logger = Logger.getLogger(HoboGroupManager.class.getName());

    public HoboGroupManager(int hobos, Dock dock, int eatingTime, int stealingTime, Map<String, Integer> recipe, Random rng) {
        this.dock = dock;
        this.eatingTime = eatingTime;
        hoboGroup = new HoboGroup(dock, hobos, stealingTime, recipe, rng);
        hoboGroup.clearFlags();
        hoboGroup.setCookers();
    }

    @Override
    public void run() {
        logger.log(INFO, "HoboGroupManager started");
        while (true) {
            synchronized (hoboGroup) {
                if (hoboGroup.canCook() && hoboGroup.getFreeHoboCount() == hoboGroup.getHoboCount()) {
                    logger.log(INFO, "Hobos started eating");
                    hoboGroup.cook();
                    try {
                        sleep(eatingTime * 1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    hoboGroup.clearFlags();
                    hoboGroup.setCookers();
                    logger.log(INFO, "Hobos finished eating");
                } else if (hoboGroup.canCook()) {
                    continue;
                } else {
                    var hoboOpt = hoboGroup.getFreeHobo();
                    if (hoboOpt.isEmpty()) {
                        continue;
                    }
                    var hobo = hoboOpt.get();
                    hobo.setFree(false);
                    var th = new Thread(hobo);
                    th.start();
                }
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
