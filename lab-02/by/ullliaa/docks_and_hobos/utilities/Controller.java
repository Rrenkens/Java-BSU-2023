package by.ullliaa.docks_and_hobos.utilities;

import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private final Model model;
    private final ArrayList<Thread> threads = new ArrayList<>();

    public Controller(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("Model is null");
        }

        this.model = model;
        controller = this;
    }

    public static Controller getController() {
        return controller;
    }

    public Model getModel() {
        return model;
    }

    public void start() {
        for (var dock : model.getDocks()) {
            Thread dockThread = new Thread(dock);
            threads.add(dockThread);
        }

        Thread hobosThread = new Thread(model.getHobos());
        threads.add(hobosThread);

        Thread shipGeneratorThread = new Thread(model.getShipGenerator());
        threads.add(shipGeneratorThread);

        for (var thread : threads) {
            thread.start();
        }
    }
}
