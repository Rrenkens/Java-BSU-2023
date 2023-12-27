package by.nrydo.docks_and_hobos;

import java.util.Random;

public class ShipGenerator implements Runnable {
    private final Tunnel tunnel;

    public ShipGenerator(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    @Override
    public void run() {
        ConfigReader config = ConfigReader.getInstance();
        while (true) {
            try {
                tunnel.addShip(generateShip());
                Thread.sleep(config.getGeneratingTime() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Ship generateShip() {
        Random random = new Random();
        ConfigReader config = ConfigReader.getInstance();
        int minCapacity = config.getShipCapacityMin();
        int maxCapacity = config.getShipCapacityMax();
        int capacity = random.nextInt(maxCapacity - minCapacity + 1) + minCapacity;
        int cargoType = random.nextInt(config.getCargoTypes().length);
        return new Ship(capacity, cargoType);
    }
}
