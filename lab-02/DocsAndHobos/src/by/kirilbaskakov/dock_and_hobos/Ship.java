package by.kirilbaskakov.dock_and_hobos;
public class Ship {
    private int cargoCapacity;
    private String cargoType;
    private int id;
    public Ship(int cargoCapacity, String cargoType, int id) {
        if (cargoCapacity <= 0) {
            MyLogger.getInstance().error("cargo capacity is non-positive");
            throw new IllegalArgumentException("cargo capacity is non-positive");
        }
        this.cargoCapacity = cargoCapacity;
        this.cargoType = cargoType;
        this.id = id;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getId() {
        return id;
    }

    public void unloadCargo(int amount) {
        cargoCapacity -= amount;
    }
}