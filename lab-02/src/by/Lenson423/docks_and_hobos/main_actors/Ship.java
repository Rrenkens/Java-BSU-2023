package by.Lenson423.docks_and_hobos.main_actors;

import org.jetbrains.annotations.NotNull;

public class Ship {
    private final int shipCapacity;
    private final String shipType;

    Ship(int shipCapacity, @NotNull String shipType) {
        if (shipCapacity < 0) {
            throw new IllegalArgumentException("SHip capacity less then 0");
        }
        this.shipCapacity = shipCapacity;
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
