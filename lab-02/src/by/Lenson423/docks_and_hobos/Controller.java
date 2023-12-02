package by.Lenson423.docks_and_hobos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private static Controller controller;
    private final Model model;
    public static Controller cetControllerInstance(List<String> cargoNames, ArrayList<Dock> docks,
                      Tunel tunel, ShipGenerator generator, HobosGroup hobosGroup){
        return Objects.requireNonNullElseGet(controller,
                () -> new Controller(cargoNames, docks, tunel, generator, hobosGroup));
    }

    private Controller(@NotNull List<@NotNull String> cargoNames,
                       @NotNull ArrayList<@NotNull Dock> docks,
                       @NotNull Tunel tunel,
                       @NotNull ShipGenerator generator,
                       @NotNull HobosGroup hobosGroup){
        controller = this;
        this.model = new Model(cargoNames, docks, tunel, generator, hobosGroup);
    }

    void startWorking(){
        List<Thread> threads = new ArrayList<>();
        for (var dock: model.getDocks()){
            Thread thread = new Thread(dock);
            threads.add(thread);
        }

        Thread hobosGroupThread = new Thread(model.getHobosGroup());
        threads.add(hobosGroupThread);

        Thread shipGeneratorThread = new Thread(model.getGenerator());
        threads.add(shipGeneratorThread);

        for (var thread: threads){
            thread.start();
        }
    }

    public Model getModel() {
        return model;
    }
    public static Controller getController() {
        return controller;
    }
}
