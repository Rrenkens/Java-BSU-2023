package by.waitingsolong.docks_and_hobos;

import java.util.*;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import by.waitingsolong.docks_and_hobos.custom.CyclicBarrier;
import by.waitingsolong.docks_and_hobos.custom.Semaphore;
import by.waitingsolong.docks_and_hobos.helpers.Job;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoboTent implements Runnable {
    private static final Logger logger = LogManager.getLogger(HoboTent.class);
    private final int hobos;
    private final int stealing_time;
    private final int crimes_per_day;
    private final int cooking_time;
    private static ArrayList<Integer> ingredients_count;
    private final Phaser workPhaser;
    private Semaphore semaphore;
    private final CyclicBarrier feastBarrier;
    private final List<Hobo> hoboList = new ArrayList<>();
    private final AtomicInteger atrociouslyLongSandwiches = new AtomicInteger(0);
    public static AtomicIntegerArray ingredientsStorage;
    private final Thread thread;
    private AtomicInteger thiefsAreDone;
    private static Random random = new Random();

    public HoboTent(int hobos, int stealing_time, int crimes_per_day, int cooking_time, ArrayList<Integer> ingredients_count) {
        if (hobos <= 2) {
            throw new IllegalArgumentException("Number of hobos must be greater than 2");
        }
        this.semaphore = new Semaphore(1);
        this.hobos = hobos;
        this.stealing_time = stealing_time;
        this.crimes_per_day = crimes_per_day;
        this.cooking_time = cooking_time;
        HoboTent.ingredients_count = ingredients_count;
        this.workPhaser = new Phaser(hobos + 1) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                logger.info("All came from work. The feast has begun. There are " + atrociouslyLongSandwiches.get() + " sandwiches on the table");
                return super.onAdvance(phase, registeredParties);
            }
        };
        this.feastBarrier = new CyclicBarrier(hobos + 1);
        this.thread = new Thread(this);
        this.thiefsAreDone = new AtomicInteger(0);
        HoboTent.ingredientsStorage = new AtomicIntegerArray(ingredients_count.size());
    }

    // semaphore guarantee that HoboTent will be executed first after the feast
    @Override
    public void run() {
        Hobo.presentIngredientsStorage();

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        while (!thread.isInterrupted()) {
            logger.info("Day " + workPhaser.getPhase());
            mobilization();
            Collections.shuffle(hoboList);
            distributeJobs();
            semaphore.release();
            workPhaser.arriveAndAwaitAdvance();
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                logger.info("Nonsense.");
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            feastBarrier.await();
            handleStates();
            logger.info("The feast is over");
        }
    }

    private void mobilization() {
        while (hoboList.size() < hobos) {
            Hobo hobo = new Hobo(this);
            hoboList.add(hobo);
            hobo.start();
        }
    }

    private void distributeJobs() {
        int cooks = 0;
        int thiefs = 0;
        for (int i = 0; i < hoboList.size(); i++) {
            Hobo hobo = hoboList.get(i);
            if (i < 2) {
                hobo.setJob(Job.Cook);
                cooks++;
            } else if (i == 3){
                hobo.setJob(Job.Thief);
                hobo.setReportingThief(true);
                thiefs++;
            }
            else {
                hobo.setJob(Job.Thief);
                thiefs++;
            }
        }
        assert(cooks + thiefs == hobos);
        thiefsAreDone.set(thiefs);
    }

    void handleStates() {
        List<Hobo> hobosToRemove = new ArrayList<>();
        for (Hobo hobo : hoboList) {
            if (!hobo.getState().dead && hobo.getState().hunger) {
                double chance = Math.random();
                if (chance < 0.25) {
                    hobosToRemove.add(hobo);
                    hobo.die();
                    logger.info(hobo.getName() + " died from hunger");
                } else if (chance < 0.5) {
                    Hobo eatenHobo = getRandomHobo();
                    hobosToRemove.add(eatenHobo);
                    eatenHobo.die();
                    logger.info(hobo.getName() + " ate " + eatenHobo.getName());
                } else {
                    logger.info(hobo.getName() + " is hungry");
                }
            }
        }

        hoboList.removeAll(hobosToRemove);
        for (Hobo hobo : hobosToRemove) {
            hobo.interrupt();
        }
    }

    public Phaser getWorkPhaser() {
        return workPhaser;
    }

    public ArrayList<Integer> getIngredientsCount() {
        return ingredients_count;
    }

    public int getStealing_time() {
        return stealing_time;
    }

    public int getCookingTime() {
        return cooking_time;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void start() {
        thread.start();
    }

    public AtomicInteger getAtrociouslyLongSandwiches() {
        return atrociouslyLongSandwiches;
    }
    public synchronized Hobo getRandomHobo() {
        if (hoboList.isEmpty()) {
            throw new RuntimeException("No hobos in HoboTent");
        }
        return hoboList.get(random.nextInt(hoboList.size()));
    }

    public CyclicBarrier getFeastBarrier() {
        return feastBarrier;
    }

    public int getCrimes_per_day() {
        return crimes_per_day;
    }

    public AtomicInteger getThiefsAreDone() {
        return thiefsAreDone;
    }

    public static int pickIngredientProportionally() {
        int totalWeight = 0;
        for (int i = 0; i < ingredients_count.size(); i++) {
            totalWeight += ingredients_count.get(i);
        }
        int randomNum = random.nextInt(totalWeight);
        int cumulativeWeight = 0;
        int ingredientIndex = -1;
        for (int i = 0; i < ingredients_count.size(); i++) {
            cumulativeWeight += ingredients_count.get(i);
            if (randomNum < cumulativeWeight) {
                ingredientIndex = i;
                break;
            }
        }
        return ingredientIndex;
    }

    public static int pickDeficitIngredient() {
        int minShareIndex = -1;
        double minShare = Double.MAX_VALUE;
        for (int i = 0; i < ingredientsStorage.length(); i++) {
            double  currShare = (double) ingredientsStorage.get(i) / ingredients_count.get(i);
            if (currShare < minShare) {
                minShare = currShare;
                minShareIndex = i;
            }
        }
        return minShareIndex;
    }
}
