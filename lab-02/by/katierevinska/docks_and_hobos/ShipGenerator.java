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

    private static int SHIP_CAPACITY_MIN;
    private static int SHIP_CAPACITY_MAX;

    Ship generate(){
        int shipCapacity = ThreadLocalRandom.current().nextInt(SHIP_CAPACITY_MIN, SHIP_CAPACITY_MAX + 1);
        int cargoType = ThreadLocalRandom.current().nextInt(0, ShipGenerator.cargoType.values().length);
        return new Ship(cargoTypes.get(cargoType), shipCapacity);
    }
}
