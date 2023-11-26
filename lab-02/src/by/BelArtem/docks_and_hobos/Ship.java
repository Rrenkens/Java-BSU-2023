package by.BelArtem.docks_and_hobos;

public class Ship {
    private final int capacity;
    private final String cargoType;

    public Ship(int capacity, String cargoType) {
        this.capacity = capacity;
        this.cargoType = cargoType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCargoType() {
        return cargoType;
    }
}
