package by.katierevinska.docks_and_hobos;

import by.katierevinska.docks_and_hobos.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Controller {
    private static Controller instance = new Controller();
    Reader reader;
    Model model;

    public static Controller getInstance() {
        return instance;
    }

    private Controller() {
        model = new Model();
    }
    public Model getModel(){
        return model;
    }

    void createObjects() throws IOException, ParseException {
        Reader reader = new Reader();
        model.createObjects(reader);
    }

    void startProcess() {
        List<Thread> threads = new ArrayList<>();

        for (var dock : model.getDocks()) {
            Thread thread = new Thread(dock);
            threads.add(thread);
        }

        Thread groupOfHobosThread = new Thread(model.getHobos());
        threads.add(groupOfHobosThread);

        Thread shipGeneratorThread = new Thread(model.getShipGenerator());
        threads.add(shipGeneratorThread);

        for (var thread : threads) {
            thread.start();
        }
    }

}
