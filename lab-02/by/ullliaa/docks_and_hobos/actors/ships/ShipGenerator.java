package by.ullliaa.docks_and_hobos.actors.ships;

import by.ullliaa.docks_and_hobos.actors.Tunnel;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable  {
    private final int generatingTime;
    private final int minShipCapacity;
    private final int maxShipCapacity;
    private final Vector<String> cargoTypes;
    private static final Logger logger = Logger.getLogger(ShipGenerator.class.getName());
    private final Tunnel tunnel;

    public ShipGenerator(int generatingTime, int minShipCapacity, int maxShipCapacity, Vector<String> cargoTypes, Tunnel tunnel) {
        if (generatingTime <= 0) {
            throw  new IllegalArgumentException("Generating time should be positive");
        }

        if (cargoTypes == null) {
            throw new IllegalArgumentException("Some parameter in ShipGenerator constructor is null");
        }

        if (cargoTypes.isEmpty()) {
            throw new IllegalArgumentException("Array of cargo types is empty");
        }

        if (maxShipCapacity - minShipCapacity < 0) {
            throw new IllegalArgumentException("Max capacity less then min capacity");
        }

        if (tunnel == null) {
            throw new IllegalArgumentException("Tunnel is null");
        }

        this.generatingTime = generatingTime;
        this.maxShipCapacity = maxShipCapacity;
        this.minShipCapacity = minShipCapacity;
        this.cargoTypes = cargoTypes;
        this.tunnel = tunnel;

        logger.log(Level.INFO, "New ship generator with generating time = " + generatingTime);
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Ship generator start work");
        while (true) {
            try {
                Thread.sleep(1000L * generatingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Ship newShip = generateShip();

            tunnel.addShips(newShip);
        }
    }

    public Ship generateShip() {
        int capacity = (int) (Math.random() * (maxShipCapacity - minShipCapacity)) + minShipCapacity;
        String cargoType = cargoTypes.get((int) (Math.random() * cargoTypes.size()));

        logger.log(Level.INFO, "Generator generate new ship with capacity = " + capacity + " and cargo type -- " + cargoType);
        return new Ship(capacity, cargoType);
    }

    public static Logger getLogger() {
        return logger;
    }

    public int getGeneratingTime() {
        return generatingTime;
    }
}
