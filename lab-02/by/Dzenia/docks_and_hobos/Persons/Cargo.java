package by.Dzenia.docks_and_hobos.Persons;

public class Cargo {
    private final String type;
    private final long weight;

    public Cargo(String type, long weight) {
        this.type = type;
        this.weight = weight;
    }

    public long getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }
}
