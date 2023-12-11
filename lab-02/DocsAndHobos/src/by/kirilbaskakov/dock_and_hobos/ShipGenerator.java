package by.kirilbaskakov.dock_and_hobos;

import java.util.Random;

public class ShipGenerator {
    private int generatingTime;
    private int shipCapacityMin;
    private int shipCapacityMax;
    private String[] cargoTypes;
    private Tunnel tunnel;
    private Thread generatorThread;
    private boolean isGenerating;
    private int shipsGenerated = 0;

    public ShipGenerator(int generatingTime, int shipCapacityMin, int shipCapacityMax, String[] cargoTypes, Tunnel tunnel) {
        if (generatingTime <= 0) {
            MyLogger.getInstance().error("Generating time must be positive");
            throw new IllegalArgumentException("Generating time must be positive");
        }
        if (cargoTypes.length == 0) {
            MyLogger.getInstance().error("Cargo types is empty");
            throw new IllegalArgumentException("Cargo types is empty");
        }
        this.generatingTime = generatingTime;
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
        this.cargoTypes = cargoTypes;
        this.tunnel = tunnel;
        this.generatorThread = null;
        this.isGenerating = false;
    }

    public void startGenerating() {
        if (isGenerating) {
            MyLogger.getInstance().error("Generator is already running.");
            throw new RuntimeException("Generator is already running.");
        }

        isGenerating = true;
        generatorThread = new Thread(() -> {
            while (isGenerating) {
                Random random = new Random();
                int cargoCapacity = random.nextInt(shipCapacityMax - shipCapacityMin + 1) + shipCapacityMin;
                String cargoType = cargoTypes[random.nextInt(cargoTypes.length)];

                Ship ship = new Ship(cargoCapacity, cargoType, shipsGenerated++);
                MyLogger.getInstance().info("Generated ship " +  ship.getId() + " with " + ship.getCargoCapacity() + " units of " + ship.getCargoType());
                tunnel.addShip(ship);

                try {
                    Thread.sleep(generatingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        generatorThread.start();
    }
}