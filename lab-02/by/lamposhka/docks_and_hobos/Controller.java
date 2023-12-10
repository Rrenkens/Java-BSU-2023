package by.lamposhka.docks_and_hobos;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Controller {

    private static Controller instance;
    private final ShipGenerator shipGenerator;
    private final Tunnel tunnel;
    private final ArrayList<Dock> docks;
    private final ArrayList<Thread> threads;
    private final Logger logger = Logger.getLogger("controllerLogger");

    public Controller(Tunnel tunnel, ShipGenerator shipGenerator, ArrayList<Dock> docks) throws Exception {
        if (instance != null) {
            throw new Exception("oh no");
        }
        instance = this;
        this.shipGenerator = shipGenerator;
        this.tunnel = tunnel;
        this.docks = docks;
        threads = new ArrayList<>();
    }

    public static Controller getInstance() {
        return instance;
    }
    public Tunnel getTunnel() {
        return tunnel;
    }

    public void start() {
        threads.add(new Thread(shipGenerator));
        for (Dock dock : docks) {
            threads.add(new Thread(dock));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void stop() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
