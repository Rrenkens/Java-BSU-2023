package by.Dzenia.docks_and_hobos.Persons;

public class Ship {
    private final Cargo cargo;

    public Ship(String cargoType, long weight) {
        cargo = new Cargo(cargoType, weight);
    }

    public Cargo getCargo() {
        return cargo;
    }

    public long getShipWeight() {
        return cargo.getWeight();
    }
}
