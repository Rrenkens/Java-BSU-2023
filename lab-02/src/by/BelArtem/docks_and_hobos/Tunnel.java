package by.BelArtem.docks_and_hobos;

public class Tunnel {
    private final int maxShips;

    private int currentShips = 0;

    public Tunnel(int max_ships) {
        this.maxShips = max_ships;
    }

    public int getMax_ships() {
        return maxShips;
    }

    public int getCurrentShips() {
        return currentShips;
    }

    void addShips(int number) {
        currentShips += number;
    }

    void removeShips(int number) {
        currentShips -= number;
    }
}
