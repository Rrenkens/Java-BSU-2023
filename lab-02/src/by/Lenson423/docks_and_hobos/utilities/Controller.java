package by.Lenson423.docks_and_hobos.utilities;

import by.Lenson423.docks_and_hobos.main_actors.Dock;
import by.Lenson423.docks_and_hobos.main_actors.HobosGroup;
import by.Lenson423.docks_and_hobos.main_actors.ShipGenerator;
import by.Lenson423.docks_and_hobos.main_actors.Tunel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.*;

public class Controller implements Runnable{
    private static Controller controller;
    private final Model model;
    private final static int FILE_CHANGING_INTERVAL = 60;
    private final String pathToLogDirectory;

    public static Controller cetControllerInstance(String path, List<String> cargoNames, ArrayList<Dock> docks,
                                                   Tunel tunel, ShipGenerator generator, HobosGroup hobosGroup) {
        return Objects.requireNonNullElseGet(controller,
                () -> new Controller(cargoNames, docks, tunel, generator, hobosGroup, path));
    }

    private Controller(@NotNull List<@NotNull String> cargoNames,
                       @NotNull ArrayList<@NotNull Dock> docks,
                       @NotNull Tunel tunel,
                       @NotNull ShipGenerator generator,
                       @NotNull HobosGroup hobosGroup,
                       @NotNull String pathToLogDirectory) {
        controller = this;
        this.model = new Model(cargoNames, docks, tunel, generator, hobosGroup);
        this.pathToLogDirectory = pathToLogDirectory + "\\log";
    }

    public void startWorking() {
        List<Thread> threads = new ArrayList<>();
        for (var dock : model.getDocks()) {
            Thread thread = new Thread(dock);
            threads.add(thread);
        }

        Thread hobosGroupThread = new Thread(model.getHobosGroup());
        threads.add(hobosGroupThread);

        Thread controllerThread = new Thread(Controller.getController());
        threads.add(controllerThread);

        Thread shipGeneratorThread = new Thread(model.getGenerator());
        threads.add(shipGeneratorThread);

        for (var thread : threads) {
            thread.start();
        }
    }

    public Model getModel() {
        return model;
    }

    public static Controller getController() {
        return controller;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(FILE_CHANGING_INTERVAL * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String filename = getNewFilenameAndCreateFile();
            changeHandlers(filename);
        }
    }

    private synchronized void changeHandlers(String filename) {
        if (model.getHandler() != null) {
            model.getHandler().flush();
        }

        for (Logger logger : model.getLoggers()) {
            logger.removeHandler(model.getHandler());
        }
        if (model.getHandler() != null) {
            model.getHandler().close();
        }

        try {
            model.setHandler(new FileHandler(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addHandler(model.getHandler());
    }

    private void addHandler(Handler handler) {
        for (Logger logger : model.getLoggers()) {
            logger.addHandler(handler);
        }
    }

    private String getNewFilenameAndCreateFile() {
        File file;
        String filename;
        try {
            do {
                filename = pathToLogDirectory + "\\logFile"
                        + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())
                        + ".txt";
                file = new File(filename);
            } while (!file.createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }
}
