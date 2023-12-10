package by.busskov.docks_and_hobos;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ship {

    public static class Generator implements Runnable {
        Generator(
                int minCapacity,
                int maxCapacity,
                int generatingTime,
                ArrayList<String> cargoTypes,
                Tunnel tunnel,
                BayLogger logger
        ) {
            if (minCapacity > maxCapacity) {
                throw new IllegalArgumentException("Min capacity is greater than max capacity");
            }
            if (generatingTime <= 0) {
                throw new IllegalArgumentException("Generating time must be positive");
            }
            if (cargoTypes == null) {
                throw new NullPointerException("Cargo types mustn't be null");
            }
            if (cargoTypes.isEmpty()) {
                throw new IllegalArgumentException("Cargo types must have values");
            }
            if (tunnel == null) {
                throw new NullPointerException("Queue for generator mustn't be null");
            }
            //TODO
            this.cargoTypes = cargoTypes;
            //TODO
            this.minCapacity = minCapacity;
            this.maxCapacity = maxCapacity;
            this.generatingTime = generatingTime;
            this.tunnel = tunnel;
            this.logger = logger;
            random = new Random();
        }

        @Override
        public void run() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int index = random.nextInt(cargoTypes.size());
                    int capacity = random.nextInt(maxCapacity - minCapacity + 1) + minCapacity;
                    Ship ship = new Ship(capacity, cargoTypes.get(index), logger);
                    tunnel.addShip(ship);
                    logger.log(Level.INFO, "Generator made new Ship: {0}", ship);
                }
            }, 0, generatingTime);
        }

        private final Random random;
        private final int generatingTime;
        private final int minCapacity;
        private final int maxCapacity;
        private final ArrayList<String> cargoTypes;
        private final BayLogger logger;

        private final Tunnel tunnel;
    }

    public Ship (
            int quantity,
            String cargoType,
            BayLogger logger
    ) {
        this.quantity = quantity;
        this.cargoType = cargoType;
        this.logger = logger;
    }

    public String getCargoType() {
        return cargoType;
    }

    public synchronized int takeGoods(int number) {
        logger.log(Level.ALL, "From {0} try to get {1} goods", new Object[] {this, number});
        if (number < quantity) {
            quantity -= number;
            return number;
        }
        int taken = quantity;
        quantity = 0;
        return taken;
    }

    public synchronized boolean isEmpty() {
        return quantity == 0;
    }

    @Override
    public String toString() {
        return "Ship:" + quantity + ";" + cargoType;
    }

    private int quantity;
    private final String cargoType;
    private final BayLogger logger;
}
