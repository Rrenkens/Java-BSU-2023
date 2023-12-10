package by.waitingsolong.docks_and_hobos;

import by.waitingsolong.docks_and_hobos.helpers.Job;
import by.waitingsolong.docks_and_hobos.helpers.State;
import by.waitingsolong.docks_and_hobos.helpers.NameDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Hobo implements Runnable {
    private static final Logger logger = LogManager.getLogger(Hobo.class);
    private final String name;
    private final HoboTent tent;
    private final Thread thread;
    private Optional<Job> job = Optional.empty();
    private static final List<String> materializationSources = Arrays.asList("ashes", "sewage", "blood", "dust", "pile of stones", "shipwreck", "garbage", "puddle on the asphalt", "rotten food leftovers");
    private static final List<String> ingredientNames = Arrays.asList("bread", "grain", "pizza", "sauce", "cheese", "tomatos");
    private State state;
    private static Random random = new Random();
    private boolean reportingThief;

    public Hobo(HoboTent hoboTent) {
        this.thread = new Thread(this);
        this.name = NameDistributor.getUniqueName();
        this.tent = hoboTent;
        this.state = new State();
        String materializationSource = materializationSources.get(random.nextInt(materializationSources.size()));
        logger.info(this.name + " has materialized from " + materializationSource);
        if (HoboTent.ingredientsStorage.length() > ingredientNames.size()) {
            throw new RuntimeException("Provide more names to Hobo.ingredientNames or reduce ingredients_count size");
        }
    }

    public void setJob(Job job) {
        this.job = Optional.ofNullable(job);
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            try {
                tent.getSemaphore().acquire();
            } catch (InterruptedException e) {
                logger.error(name + " interrupted while acquiring semaphore to start working");
                Thread.currentThread().interrupt();
            }
            tent.getSemaphore().release();
            if (job.isPresent()) {
                switch (job.get()) {
                    case Cook -> cook();
                    case Thief -> steal();
                }
            }
            logger.info(name + " finished working");
            tent.getWorkPhaser().arriveAndAwaitAdvance();
            eat();
            tent.getFeastBarrier().await();
            try {
                tent.getSemaphore().acquire();
                tent.getSemaphore().release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void eat() {
        AtomicInteger atrociouslyLongSandwiches = tent.getAtrociouslyLongSandwiches();
        if (atrociouslyLongSandwiches.get() > 0) {
            atrociouslyLongSandwiches.decrementAndGet();
            state.hunger = false;
            logger.info(name + " got sandwich");
        } else {
            state.hunger = true;
        }
    }

    public String getName() {
        return this.name;
    }

    private void cook() {
        logger.info(name + " cooks");
        while (tent.getThiefsAreDone().get() > 0) {
            AtomicIntegerArray storage = HoboTent.ingredientsStorage;
            boolean enoughIngredients = true;

            synchronized (storage) {
                for (int i = 0; i < storage.length(); i++) {
                    if (storage.get(i) < tent.getIngredientsCount().get(i)) {
                        enoughIngredients = false;
                        break;
                    }
                }

                if (enoughIngredients) {
                    logger.info(name + " started cooking!");
                    for (int i = 0; i < storage.length(); i++) {
                        storage.getAndAdd(i, -tent.getIngredientsCount().get(i));
                    }
                    try {
                        Thread.sleep( 1000L * tent.getCookingTime());
                    } catch(InterruptedException e) {
                        logger.error(name + " interrupted while cooking");
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    tent.getAtrociouslyLongSandwiches().incrementAndGet();
                    logger.info(name + " made a sandwich");
                } else {
                    logger.info(name + " is waiting for ingredients...");
                    try {
                        Thread.sleep(tent.getStealing_time() * 1000L);
                    } catch(InterruptedException e) {
                        logger.error(name + " interrupted while waiting ingredients");
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    private void steal() {
        logger.info(name + " steals");
        int counter = 0;
        while (counter++ != tent.getCrimes_per_day()) {
            if (reportingThief) {
                logIngredientsStorage();
            }

            AtomicIntegerArray storage = HoboTent.ingredientsStorage;
            int ingredientIndex = HoboTent.pickDeficitIngredient();
            try {
                Thread.sleep(tent.getStealing_time() * 1000L);
            } catch(InterruptedException e) {
                logger.error(name + " interrupted while stealing");
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            logger.info(name + " stole " + ingredientNames.get(ingredientIndex));
            storage.getAndIncrement(ingredientIndex);
        }
        if (reportingThief) reportingThief = false;
        tent.getThiefsAreDone().decrementAndGet();
    }

    private static void logIngredientsStorage() {
        AtomicIntegerArray ingredientsStorage = HoboTent.ingredientsStorage;
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < ingredientsStorage.length() * 5; i++) {
            sb.append("-");
        }
        sb.append("\n| ");
        for (int i = 0; i < ingredientsStorage.length(); i++) {
            sb.append(ingredientsStorage.get(i));
            if (i < ingredientsStorage.length() - 1) {
                sb.append(" | ");
            }
        }
        sb.append(" |\n");
        for (int i = 0; i < ingredientsStorage.length() * 5; i++) {
            sb.append("-");
        }
        logger.info(sb.toString());
    }

    static void presentIngredientsStorage() {
        logger.info("This is an ingredients storage:");
        logIngredientsStorage();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HoboTent.ingredientsStorage.length(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(ingredientNames.get(i));
        }
        logger.info("It consists of " + sb.toString());
    }

    void die() {
        state.dead = true;
    }

    void interrupt() {
        thread.interrupt();
    }

    public void start() {
        thread.start();
    }

    public State getState() {
        return state;
    }

    public void setReportingThief(boolean reportingThief) {
        this.reportingThief = reportingThief;
    }
}
