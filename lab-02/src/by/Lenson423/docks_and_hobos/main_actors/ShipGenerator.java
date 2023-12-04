package by.Lenson423.docks_and_hobos.main_actors;

import by.Lenson423.docks_and_hobos.utilities.Controller;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable {
    private final int generationTime;
    private final Timer timer;
    private final int shipCapacityMin;
    private final int shipCapacityMax;
    private static final Logger logger = Logger.getLogger(ShipGenerator.class.toString());

    final private TimerTask task = new TimerTask() {
        public void run() {
            List<String> cargoList = Controller.getController().getModel().getCargoTypes().getCargoNames();
            Controller.getController().getModel().getTunel().tryAddShipToTunel
                    (new Ship(ThreadLocalRandom.current().nextInt(shipCapacityMin, shipCapacityMax + 1),
                            cargoList.get(ThreadLocalRandom.current().nextInt(cargoList.size()))));
        }
    };

    public ShipGenerator(int generationTime, int shipCapacityMin, int shipCapacityMax) {
        if (generationTime <= 0) {
            throw new IllegalArgumentException("Invalid period");
        }
        this.generationTime = generationTime;
        this.timer = new Timer();

        if (shipCapacityMin < 0) {
            throw new IllegalArgumentException("Min capacity is invalid");
        }
        if (shipCapacityMax - shipCapacityMin < 0) {
            throw new IllegalArgumentException("Max capacity less then min or max capacity is invalid");
        }
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Ship generator start working");
        timer.scheduleAtFixedRate(task, 0, generationTime * 1000L);
    }
}
