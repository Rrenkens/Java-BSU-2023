package by.Dzenia.docks_and_hobos.Controller;
import by.Dzenia.docks_and_hobos.RunnableObjects.Dock;
import java.io.IOException;
import java.util.ArrayList;

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
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(new Thread(model.getShipGenerator()));
        for (Dock dock: model.getDocks()) {
            threads.add(new Thread(dock));
        }
        threads.add(new Thread(model.getHobos()));
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {

            }
        }
    }
}
