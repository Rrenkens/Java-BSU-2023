package by.MikhailShurov.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Bay implements Runnable{
    private static final Logger logger = LoggerUtil.getLogger();
    Tunnel tunnel_;
    ArrayList<Dock> docks_;
    HobosCamp hobosCamp_;

    public class HobosCamp implements Runnable {
        HashMap<String, Double> portionIngredients_ = new HashMap<>();
        volatile HashMap<String, Double> collectedIngredients = new HashMap<>();
        ArrayList<Hobo> hobos_ = new ArrayList<>();

        void allHobosEating() throws InterruptedException {
            for (int i = 0; i < hobos_.size(); ++i) {
                hobos_.get(i).eat();
            }
        }

        public class Hobo implements Runnable {
            Integer EATING_TIME = 20000;
            Integer STEALING_TIME = 1000;

            void eat() throws InterruptedException {
                Thread.sleep(EATING_TIME);
            }

            void steal(String ingredient) {
                boolean cargoStolen = false;
                while (!cargoStolen) {
                    for (int i = 0; i < docks_.size(); ++i) {
                        if (docks_.get(i).getCargoCapacity(ingredient) != 0) {
                            docks_.get(i).stealCargo(ingredient);
                            cargoStolen = true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (!allIngredientsCollected()) {
                            for (String ingredient : portionIngredients_.keySet()) {
                                if (portionIngredients_.get(ingredient) - collectedIngredients.get(ingredient) != 0) {
                                    collectedIngredients.put(ingredient, collectedIngredients.get(ingredient) + 1);
                                    steal(ingredient);
                                    logger.info("stolen " + ingredient + " amount = 1");
                                    logger.info("collected ingredients = " + collectedIngredients);
                                    logger.info("recipe ingredients = " + portionIngredients_ + "\n");
                                    System.out.println();
                                    System.out.println("HOBOS CAMP: stolen = " + ingredient + ", amount = 1");
                                    System.out.println("HOBOS CAMP: collected ingredients: " + collectedIngredients);
                                    System.out.println("HOBOS CAMP: recipe ingredients: " + portionIngredients_);
                                    System.out.println();
                                    try {
                                        Thread.sleep(STEALING_TIME);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            try {
                                logger.info("Now hobos start eating. Eating time = " + EATING_TIME / 1000 + " seconds\n");
                                System.out.println("HOBOS CAMP: Now hobos start eating. Eating time = " + EATING_TIME / 1000 + " seconds\n");
                                allHobosEating();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

        HobosCamp(Integer hobosAmount) {
            if (hobosAmount < 3) {
                hobosAmount = 3;
            }
            portionIngredients_.put("cocaine", 2.0);
            portionIngredients_.put("heroin", 2.0);
            portionIngredients_.put("marijuana", 2.0);
            portionIngredients_.put("pineapple", 1.0);

            collectedIngredients.put("cocaine", 0.0);
            collectedIngredients.put("heroin", 0.0);
            collectedIngredients.put("marijuana", 0.0);
            collectedIngredients.put("pineapple", 0.0);

            for (int i = 0; i < hobosAmount - 2; ++i) {
                Hobo hobo = new Hobo();
                hobos_.add(hobo);
            }
        }

        boolean allIngredientsCollected() {
            for (String ingredient : portionIngredients_.keySet()) {
                if ((double)portionIngredients_.get(ingredient) != (double)collectedIngredients.get(ingredient)) {
                    return false;
                }
            }
            for (String ingredient : collectedIngredients.keySet()) {
                collectedIngredients.put(ingredient, 0.0);
            }
            return true;
        }

        @Override
        public void run() {
            for (int i = 0; i < hobos_.size(); ++i) {
                Thread hubbabubba = new Thread(hobos_.get(i));
                hubbabubba.start();
            }
        }
    }

    public Bay(int dockAmount, Tunnel tunnel) {
        tunnel_ = tunnel;
        docks_ = new ArrayList<>();


        for (int i = 0; i < dockAmount; ++ i) {
            Dock dock = new Dock(1, 20);
            Thread thread = new Thread(dock);
            thread.start();
            docks_.add(dock);
        }
        hobosCamp_ = new HobosCamp(3);
        Thread hobosCamp = new Thread(hobosCamp_);
        hobosCamp.start();
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            for (Dock dock : docks_) {
                while (dock.shipIsNull()) {
                    try {
                        Ship ship = tunnel_.getShip();
                        dock.setShip(ship);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
