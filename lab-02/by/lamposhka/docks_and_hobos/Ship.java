package by.lamposhka.docks_and_hobos;

public class Ship {
    private String cargoType;
    private int load;

    public Ship(String cargoType, int load) {
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
