package by.mnik0_0.docks_and_hobos;

public class Ship {
    private final int capacity;
    private final String cargoType;
    private String name;

    public String getName() {
        return name;
    }

    public Ship(int capacity, String cargoType, String name) {
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void sink() {
        System.out.printf("Ship sunk %s%n", name);
    }
}
