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
    private final ArrayList<Logger> loggers;

    public Model(ArrayList<Dock> docks,
                 Hobos hobos,
                 ShipGenerator shipGenerator,
                 Tunnel tunnel,
                 Vector<String> cargoNames) {
        if (docks.isEmpty()) {
            throw new IllegalArgumentException("Illegal docks array");
        }

        for (var dock : docks) {
            if (dock == null) {
                throw new IllegalArgumentException("Illegal dock in docks array");
            }
        }

        if (hobos == null) {
            throw new IllegalArgumentException("Illegal hobos array");
        }

        if (shipGenerator == null) {
            throw new IllegalArgumentException("Illegal ship generator");
        }

        if (tunnel == null) {
            throw new IllegalArgumentException("Illegal tunnel");
        }

        if (cargoNames == null) {
            throw new IllegalArgumentException("Cargo names can't be null");
        }

        if (cargoNames.isEmpty() || cargoNames.contains("")) {
            throw new IllegalArgumentException("Illegal cargo names");
        }

        this.docks = docks;
        this.hobos = hobos;
        this.shipGenerator = shipGenerator;
        this.tunnel = tunnel;
        this.cargoTypes = new CargoTypes(cargoNames);

        loggers = new ArrayList<>();
        loggers.add(Dock.getLogger());
        loggers.add(Hobos.getLogger());
        loggers.add(ShipGenerator.getLogger());
        loggers.add(Tunnel.getLogger());
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

    public ArrayList<Logger> getLoggers() {
        return loggers;
    }

}