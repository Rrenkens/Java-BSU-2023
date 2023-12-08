package by.Lenson423.docks_and_hobos.utilities;

import by.Lenson423.docks_and_hobos.main_actors.Dock;
import by.Lenson423.docks_and_hobos.main_actors.HobosGroup;
import by.Lenson423.docks_and_hobos.main_actors.ShipGenerator;
import by.Lenson423.docks_and_hobos.main_actors.Tunel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Model {
    private final CargoTypes cargoTypes;
    private final ArrayList<Dock> docks;
    private final Tunel tunel;
    private final ShipGenerator generator;
    private final HobosGroup hobosGroup;
    private final ArrayList<@NotNull Logger> loggers;
    private Handler handler;

    public Model(@NotNull List<@NotNull String> cargoNames,
                 @NotNull ArrayList<@NotNull Dock> docks,
                 @NotNull Tunel tunel,
                 @NotNull ShipGenerator generator,
                 @NotNull HobosGroup hobosGroup) {

        if (cargoNames.isEmpty() || cargoNames.contains("")) {
            throw new IllegalArgumentException("Illegal cargo names array");
        }
        this.cargoTypes = new CargoTypes(cargoNames);

        if (docks.isEmpty()) {
            throw new IllegalArgumentException("Illegal docks array");
        }
        this.docks = docks;
        this.tunel = tunel;
        this.generator = generator;
        this.hobosGroup = hobosGroup;

        loggers = new ArrayList<>();
        loggers.add(Tunel.getLogger());
        loggers.add(ShipGenerator.getLogger());
        loggers.add(Dock.getLogger());
        loggers.add(HobosGroup.getLogger());
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

    public ArrayList<Logger> getLoggers() {
        return loggers;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
