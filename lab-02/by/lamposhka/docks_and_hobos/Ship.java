package by.lamposhka.docks_and_hobos;

public class Ship {
    private int capacity;
    private String cargoType;
    private int load;

    public Ship(int capacity, String cargoType, int load) {
        if (load > capacity) {
            throw new IndexOutOfBoundsException("Load of ship cannot be bigger than capacity.");
        }
        // invalid cargo type
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.load = load;
    }
    public String getCargoType() {
        return cargoType;
    }
    public int getLoad() {
        return load;
    }

}
