package by.AlexHanimar.docs_and_hobos;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import by.AlexHanimar.docs_and_hobos.Dock;
import by.AlexHanimar.docs_and_hobos.HoboGroupInterface;

import static java.lang.Thread.sleep;
import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.INFO;

public class Hobo implements Runnable {
    private boolean isFree;
    private boolean isCooking;
    private String product;
    private final String name;
    private final Random rng;
    private final int stealingTime;
    private static final Logger logger = Logger.getLogger(Hobo.class.getName());

    private final Dock dock;
    private final HoboGroupInterface group;

    public Hobo(String name, Dock dock, HoboGroupInterface group, int stealingTime, Random rng) {
        this.dock = dock;
        this.group = group;
        this.rng = rng;
        this.stealingTime = stealingTime;
        this.name = name;
    }

    public boolean isFree() {
        return isFree;
    }

    public boolean isCooking() {
        return isCooking;
    }

    public String getProduct() {
        return product;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setCooking(boolean cooking) {
        isCooking = cooking;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    synchronized public boolean Steal() throws InterruptedException {
        synchronized (dock) {
            var list = new ArrayList<String>();
            for (var key : dock.getCount().keySet()) {
                if (dock.getCount().get(key) > 0) {
                    list.add(key);
                }
            }
            if (list.isEmpty()) {
                return false;
            }
            product = list.get(rng.nextInt(list.size()));
            var val = dock.getCount().get(product);
            dock.getCount().replace(product, val - 1);
        }
        synchronized (group) {
            var val = group.getSupply().get(product);
            group.getSupply().replace(product, val + 1);
        }
        sleep(stealingTime * 1000L);
        return true;
    }

    @Override
    synchronized public void run() {
        try {
            boolean success = Steal();
            if (success) {
                logger.log(CONFIG, "Hobo " + name + " has successfully stolen " + product);
            }
        } catch (Exception ignored) {

        } finally {
            setFree(true);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
