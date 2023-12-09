package by.katierevinska.docks_and_hobos.model;

class Ship {
    private String cargoType;
    private Long shipCapacity;
    private Long shipId;
    Long getShipId(){
        return shipId;
    }
    void setShipId(Long id){
        shipId = id;
    }

    Long getShipCapacity() {
        return shipCapacity;
    }
    String getCargoType() {
        return cargoType;
    }

    Ship(String cargoType, Long capacity, Long id) {
        this.cargoType = cargoType;
        this.shipCapacity = capacity;
        this.shipId = id;
    }
}
