package by.ullliaa.docks_and_hobos.utilities;

import by.ullliaa.docks_and_hobos.actors.CargoTypes;
import by.ullliaa.docks_and_hobos.actors.*;
import by.ullliaa.docks_and_hobos.actors.ships.ShipGenerator;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

public class Model {
    private final CargoTypes cargoTypes;
    private final ArrayList<Dock> docks;
    private final Tunnel tunnel;
    private final ShipGenerator shipGenerator;
    private final Hobos hobos;

    public Model(ArrayList<Dock> docks,
                 Hobos hobos,
                 ShipGenerator shipGenerator,
                 Tunnel tunnel,
                 Vector<String> cargoTypes) {
        if (docks == null) {
            throw new IllegalArgumentException("Docks is null");
        }

        if (docks.isEmpty()) {
            throw new IllegalArgumentException("Docks is empty");
        }

        for (var dock : docks) {
            if (dock == null) {
                throw new IllegalArgumentException("Dock in docks array is null");
            }
        }

        if (hobos == null) {
            throw new IllegalArgumentException("Hobos array is null");
        }

        if (shipGenerator == null) {
            throw new IllegalArgumentException("Ship generator is null");
        }

        if (tunnel == null) {
            throw new IllegalArgumentException("Tunnel is null");
        }

        if (cargoTypes == null) {
            throw new IllegalArgumentException("Cargo names can't be null");
        }

        if (cargoTypes.isEmpty()) {
            throw new IllegalArgumentException("Cargo names is empty");
        }

        if (cargoTypes.contains("")) {
            throw new IllegalArgumentException("Cargo names contains illegal name");
        }

        this.docks = docks;
        this.hobos = hobos;
        this.shipGenerator = shipGenerator;
        this.tunnel = tunnel;
        this.cargoTypes = new CargoTypes(cargoTypes);
    }

    public CargoTypes getCargoTypes() {
        return cargoTypes;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    public Hobos getHobos() {
        return hobos;
    }
}