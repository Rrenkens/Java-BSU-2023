package by.Dzenia.docks_and_hobos.Persons;

public class Ship {
    private final Cargo cargo;
    private final int weight;

    public Ship(String cargoType, int weight) {
        cargo = new Cargo(cargoType);
        this.weight = weight;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public int getWeight() {
        return weight;
    }
}
