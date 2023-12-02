package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static Controller controller;
    private final CargoTypes cargoTypes;
    private final ArrayList<Dock> docks;

    private final Tunel tunel;

    private final ShipGenerator generator;

    public Controller(List<String> cargoNames, ArrayList<Dock> docks,
                      @NotNull Tunel tunel,
                      @NotNull ShipGenerator generator) {
        controller = this;

        if (cargoNames == null || cargoNames.contains(null) || cargoNames.isEmpty() || cargoNames.contains("")){
            throw new IllegalArgumentException("Illegal cargo names array");
        }
        this.cargoTypes = new CargoTypes(cargoNames);

        if (docks == null || docks.contains(null) || docks.isEmpty()){
            throw new IllegalArgumentException("Illegal docks array");
        }
        this.docks = docks;
        this.tunel = tunel;
        this.generator = generator;
    }

    public static Controller getController() {
        return controller;
    }

    public CargoTypes getCargoTypes() {
        return cargoTypes;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }

    public Tunel getTunel() {
        return tunel;
    }

    public ShipGenerator getGenerator() {
        return generator;
    }
}
