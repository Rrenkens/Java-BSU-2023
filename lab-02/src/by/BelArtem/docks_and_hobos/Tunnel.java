package by.BelArtem.docks_and_hobos;

public class Tunnel {
    private final int maxShips;

    private int shipsInTunnel = 0;

    public Tunnel(int max_ships) {
        this.maxShips = max_ships;
    }

    public int getMaxShips() {
        return maxShips;
    }

    public void setShipsInTunnel(int num) {
        this.shipsInTunnel = num;
    }

    public boolean isFull () {
        return shipsInTunnel == maxShips;
    }


    void removeShips(int number) {
        shipsInTunnel -= number;
    }
}
