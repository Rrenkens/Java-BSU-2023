package by.ullliaa.docks_and_hobos.actors;

import java.util.*;

public class CargoTypes {
    private final Vector<String> cargoNames = new Vector<>();

    public CargoTypes(Vector<String> names) {
        if (names == null) {
            throw new IllegalArgumentException("Cargo types is null");
        }

        if (names.isEmpty()) {
            throw new IllegalArgumentException("Array of cargo types is empty");
        }

        for (var el: names) {
            if (el == null) {
                throw new IllegalArgumentException("Element in cargo types is null");
            }
        }

        if (names.contains("")) {
            throw new IllegalArgumentException("List with names is invalid");
        }

        cargoNames.addAll(names);
    }

    public int getName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name of cargo is null");
        }

        for (int i = 0; i < cargoNames.size(); ++i) {
            if (Objects.equals(cargoNames.get(i), name)) {
                return i;
            }
        }

        throw new RuntimeException("Don't have such product");
    }

    public Vector<String> getCargoNames(){
        return cargoNames;
    }
}