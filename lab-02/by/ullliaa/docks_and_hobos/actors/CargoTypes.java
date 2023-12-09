package by.ullliaa.docks_and_hobos.actors;

import java.util.Objects;
import java.util.Vector;

public class CargoTypes {
    private final Vector<String> cargoTypes = new Vector<>();

    public CargoTypes(Vector<String> types) {
        if (types == null) {
            throw new IllegalArgumentException("Cargo types array is null");
        }

        if (types.isEmpty()) {
            throw new IllegalArgumentException("Array of cargo types is empty");
        }

        for (var el: types) {
            if (el == null) {
                throw new IllegalArgumentException("Element in cargo types is null");
            }
        }

        if (types.contains("")) {
            throw new IllegalArgumentException("Types contain invalid name");
        }

        cargoTypes.addAll(types);
    }

    public int getType(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name of cargo is null");
        }

        for (int i = 0; i < cargoTypes.size(); ++i) {
            if (Objects.equals(cargoTypes.get(i), name)) {
                return i;
            }
        }

        throw new RuntimeException("Don't have such type");
    }

    public Vector<String> getCargoTypes(){
        return cargoTypes;
    }
}