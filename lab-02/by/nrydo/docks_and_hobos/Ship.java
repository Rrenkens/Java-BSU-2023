package by.nrydo.docks_and_hobos;

public class Ship {
    private int store;
    private final int cargoType;

    public Ship(int capacity, int cargoType) {
        this.store = capacity;
        this.cargoType = cargoType;
    }

    public int getStore() {
        return store;
    }

    public int getCargoType() {
        return cargoType;
    }

    public String getTypeName() {
        return ConfigReader.getInstance().getCargoTypes()[cargoType];
    }

    public int take(int x) {
        if (store > x) {
            store -= x;
            return x;
        }
        int result = store;
        store = 0;
        return result;
    }
}
