package by.BelArtem.docks_and_hobos;

public class Ship {
    private final int capacity;

    private int cargoLeft;
    private final String cargoType;

    public Ship(int capacity, String cargoType) {
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.cargoLeft = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getCargoLeft() {
        return cargoLeft;
    }

    public void unloadShip (int num) {
        this.cargoLeft -= num;
    }

}
