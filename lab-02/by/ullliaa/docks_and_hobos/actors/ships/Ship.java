package by.ullliaa.docks_and_hobos.actors.ships;

public class Ship {
    private final int shipCapacity;
    private final String shipType;

    public Ship(int shipCapacity, String shipType) {
        if (shipCapacity < 0) {
            throw new IllegalArgumentException("Ship capacity is less then zero");
        }

        this.shipCapacity = shipCapacity;

        if (shipType == null) {
            throw new IllegalArgumentException("Ship type is null");
        }

        if (shipType.isEmpty()) {
            throw new IllegalArgumentException("Ship type is empty");
        }

        this.shipType = shipType;
    }

    public int getShipCapacity() {
        return shipCapacity;
    }

    public String getShipType() {
        return shipType;
    }
}
