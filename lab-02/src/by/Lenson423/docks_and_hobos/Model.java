package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final CargoTypes cargoTypes;
    private final ArrayList<Dock> docks;
    private final Tunel tunel;
    private final ShipGenerator generator;
    private final HobosGroup hobosGroup;

    public Model(@NotNull List<@NotNull String> cargoNames,
                       @NotNull ArrayList<@NotNull Dock> docks,
                       @NotNull Tunel tunel,
                       @NotNull ShipGenerator generator,
                       @NotNull HobosGroup hobosGroup) {

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
        this.hobosGroup = hobosGroup;
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

    public HobosGroup getHobosGroup() {
        return hobosGroup;
    }

}
