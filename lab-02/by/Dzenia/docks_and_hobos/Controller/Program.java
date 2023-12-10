package by.Dzenia.docks_and_hobos.Controller;

import by.Dzenia.docks_and_hobos.RunnableObjects.Dock;

import java.io.IOException;

public class Program implements Runnable{

    private Model model;

    public Program(String pathToFile) throws IOException {
        this.model = new Model(pathToFile);
    }

    public void start() {
        run();
    }
    @Override
    public void run() {
        model.getShipGenerator().run();
        for (Dock dock: model.getDocks()) {
            dock.run();
        }
    }
}
