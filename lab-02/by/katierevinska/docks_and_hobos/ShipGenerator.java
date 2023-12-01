package by.katierevinska.docks_and_hobos;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ShipGenerator {
    enum cargoType{
        Tomatoes,
        Cucumbers,
        Bread,
        Sausages,
        Butter
    }
    List<String> cargoTypes = Arrays.stream(cargoType.values()).map(Objects::toString).toList();

    private Long SHIP_CAPACITY_MIN;
    private Long SHIP_CAPACITY_MAX;
    private Long GENERATION_TIME;

    ShipGenerator setGeneratingTime(Long time){
        GENERATION_TIME = time;
        return this;
    }
    ShipGenerator setShipCapacityMin(Long capacity){
        SHIP_CAPACITY_MIN = capacity;
        return this;
    }
    ShipGenerator setShipCapacityMax(Long capacity){
        SHIP_CAPACITY_MAX = capacity;
        return this;
    }
    Long getGeneratingTime(){
        return GENERATION_TIME;
    }
    Ship generate(){
        Long shipCapacity = ThreadLocalRandom.current().nextLong(SHIP_CAPACITY_MIN, SHIP_CAPACITY_MAX + 1);
        int cargoType = ThreadLocalRandom.current().nextInt(0, ShipGenerator.cargoType.values().length);
        return new Ship(cargoTypes.get(cargoType), shipCapacity);
    }
}
