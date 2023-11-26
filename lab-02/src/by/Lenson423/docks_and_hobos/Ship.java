package by.Lenson423.docks_and_hobos;

public class Ship {
    final int shipCapacity;
    final String shipType;

    Ship(int shipCapacity, String shipType){
        if (shipCapacity < 0){
            throw new IllegalArgumentException("SHip capacity less then 0");
        }
        this.shipCapacity = shipCapacity;
        if (shipType == null || shipType.isEmpty()){
            throw new IllegalArgumentException("Ship type is empty");
        }
        this.shipType = shipType;
    }
}
