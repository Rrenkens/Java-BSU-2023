package by.katierevinska.docks_and_hobos.model;

class Ship {
    private String cargoType;
    private Long shipCapacity;

    Long getShipCapacity() {
        return shipCapacity;
    }
    String getCargoType() {
        return cargoType;
    }

    Ship(String cargoType, Long capacity) {
        this.cargoType = cargoType;
        this.shipCapacity = capacity;
    }
}
