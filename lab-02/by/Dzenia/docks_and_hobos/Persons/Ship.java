package by.Dzenia.docks_and_hobos.Persons;

public class Ship {
    private final Cargo cargo;
    private final long weight;

    public Ship(String cargoType, long weight) {
        cargo = new Cargo(cargoType);
        this.weight = weight;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public long getWeight() {
        return weight;
    }
}
