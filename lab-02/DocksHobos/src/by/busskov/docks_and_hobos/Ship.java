package by.busskov.docks_and_hobos;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Ship {

    public static class Generator {
        Generator(
                int minCapacity,
                int maxCapacity,
                int generatingTime,
                ArrayList<String> cargoTypes,
                Tunnel tunnel
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
            random = new Random();
        }

        void startGenerating() {
            //TODO
        }

        Ship generate() {
            int index = random.nextInt(cargoTypes.size());
            int capacity = random.nextInt(maxCapacity - minCapacity + 1) + minCapacity;
            return new Ship(capacity, cargoTypes.get(index));
        }

        private final Random random;
        private final int generatingTime;
        private final int minCapacity;
        private final int maxCapacity;
        private final ArrayList<String> cargoTypes;

        private final Tunnel tunnel;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCargoType() {
        return cargoType;
    }

    public Ship (
            int capacity,
            String cargoType
    ) {
        if (cargoType == null || cargoType.isBlank()) {
            throw new IllegalArgumentException("Cargo type must have a value");
        }
        this.capacity = capacity;
        this.cargoType = cargoType;
    }
    private final int capacity;
    private final String cargoType;
}
