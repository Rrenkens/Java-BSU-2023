package by.AlexHanimar.docs_and_hobos;

import java.util.List;
import java.util.Random;

import by.AlexHanimar.docs_and_hobos.Ship;

public class ShipGenerator {
    private final int shipCapacityMin;
    private final int shipCapacityMax;
    private final Random rng;
    private int lastShipId;
    private final List<String> cargoTypes;

    public ShipGenerator(int shipCapacityMin, int shipCapacityMax, List<String> cargoTypes, Random rng) {
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
        this.cargoTypes = cargoTypes;
        this.rng = rng;
    }

    public Ship GenerateShip() {
        String cargoType = cargoTypes.get(rng.nextInt(cargoTypes.size()));
        int count = rng.nextInt(shipCapacityMin, shipCapacityMax + 1);
        lastShipId += 1;
        return new Ship(String.valueOf(lastShipId - 1), new Product(cargoType, count));
    }

    public List<String> getCargoTypes() {
        return cargoTypes;
    }

    public int getShipCapacityMin() {
        return shipCapacityMin;
    }

    public int getShipCapacityMax() {
        return shipCapacityMax;
    }
}
