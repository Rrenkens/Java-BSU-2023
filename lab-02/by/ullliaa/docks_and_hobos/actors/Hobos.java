package by.ullliaa.docks_and_hobos.actors;

import by.ullliaa.docks_and_hobos.utilities.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Hobos implements Runnable {
    private final int eatingTime;
    private final int[] ingredientsCount;
    private final AtomicIntegerArray currentIngredientsCount;
    public final ArrayList<Hobo> hobos;
    private final Vector<Integer> isStolen = new Vector<>();
    private static final Logger logger = Logger.getLogger(Hobos.class.getName());

    public Hobos(int[] ingredientsCount, int eatingTime, ArrayList<Hobo> hobos) {
        if (ingredientsCount == null) {
            throw new IllegalArgumentException("Ingredient count is null");
        }

        for (var elem : ingredientsCount) {
            if (elem < 0) {
                throw new IllegalArgumentException("Count of ingredient should be positive");
            }
        }

        if (eatingTime <= 0) {
            throw new IllegalArgumentException("Eating time should be positive");
        }

        if (hobos == null) {
            throw new IllegalArgumentException("Hobos is hull");
        }

        if (hobos.size() <= 2) {
            throw new IllegalArgumentException("Count of hobos should be more than 2");
        }

        for (var hobo : hobos) {
            if (hobo == null) {
                throw new IllegalArgumentException("Some hobo in hobos is null");
            }
        }

        this.ingredientsCount = ingredientsCount;
        this.eatingTime = eatingTime;
        this.hobos = hobos;
        currentIngredientsCount = new AtomicIntegerArray(ingredientsCount.length);

        for (int i = 0; i < ingredientsCount.length; ++i) {
            isStolen.add(i, 0);
        }
    }

    public void eat() throws InterruptedException {
        logger.log(Level.INFO, "Begin eat");

        for (int i = 0; i < ingredientsCount.length; ++i) {
            currentIngredientsCount.set(i, currentIngredientsCount.get(i) - ingredientsCount[i]);
        }

        sleep(eatingTime * 1000L);
        logger.log(Level.INFO, "Eating is over");

        for (var elem : isStolen) {
            elem = 0;
        }
    }

    public int productForSteal() {
        int product = (int) (Math.random() * ingredientsCount.length);
        while (true) {
            if (ingredientsCount[product] > currentIngredientsCount.get(product)) {
                return product;
            } else {
                isStolen.set(product, 1);
                product = (int) (Math.random() * ingredientsCount.length);

                boolean allIngStolen = true;
                for (var elem : isStolen) {
                    if (elem == 0) {
                        allIngStolen = false;
                        break;
                    }
                }

                if (allIngStolen) {
                    return -1;
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            int fChef = (int) (Math.random() * hobos.size());
            int sChef = (int) (Math.random() * hobos.size());

            while (sChef == fChef) {
                sChef = (int) (Math.random() * hobos.size());
            }

            logger.log(Level.INFO, "the first chef is hobo number " + fChef);
            logger.log(Level.INFO, "the second chef is hobo number " + sChef);

            logger.log(Level.INFO, "The beginning of the stealing");
            List<Thread> threads = getThreads(fChef, sChef);

            for (var thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }

            logger.log(Level.INFO, "The stealing is over");

            try {
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Thread> getThreads(int fChef, int sChef) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < hobos.size(); ++i) {
            if (i != fChef && i != sChef) {
                int curHobo = i;
                Thread thread = new Thread(() -> {
                    hobos.get(curHobo).run();
                });

                thread.start();
                threads.add(thread);
            }
        }
        return threads;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static class Hobo implements Runnable {
        private final int stealingTime;
        int id;

        public Hobo(int stealingTime, int id) {
            if (stealingTime <= 0) {
                throw new IllegalArgumentException("Stealing time should be positive");
            }

            if (id < 0) {
                throw new IllegalArgumentException("Hobo id should be positive");
            }

            this.stealingTime = stealingTime;
            this.id = id;
        }

        void steal(int productIndex) throws InterruptedException {
            boolean flag = false;
            while (true) {
                List<Dock> docks = Controller.getController().getModel().getDocks();
                for (var dock : docks) {
                    if (dock.stealProduct(productIndex)) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    try {
                        sleep(stealingTime * 1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Controller.getController().getModel().
                            getHobos().currentIngredientsCount.incrementAndGet(productIndex);
                    break;
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                int product = Controller.getController().getModel().getHobos().productForSteal();
                if (product != -1) {
                    try {
                        steal(product);

                        logger.log(Level.INFO, "Hobo " + id + " steal " +
                                Controller.getController().getModel().getCargoTypes().getCargoTypes().get(product));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    break;
                }
            }
        }
    }
}