package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private static Controller controller;
    private final CargoTypes cargoTypes;
    private final ArrayList<Dock> docks;
    private final Tunel tunel;
    private final ShipGenerator generator;

    public static Controller cetControllerInstance(List<String> cargoNames, ArrayList<Dock> docks,
                      Tunel tunel,
                      ShipGenerator generator){
        return Objects.requireNonNullElseGet(controller, () -> new Controller(cargoNames, docks, tunel, generator));
    }

    private Controller(@NotNull List<@NotNull String> cargoNames,
                       @NotNull ArrayList<@NotNull Dock> docks,
                      @NotNull Tunel tunel,
                      @NotNull ShipGenerator generator) {
        controller = this;

        if (cargoNames.isEmpty() || cargoNames.contains("")){
            throw new IllegalArgumentException("Illegal cargo names array");
        }
        this.cargoTypes = new CargoTypes(cargoNames);

        if (docks.isEmpty()){
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
