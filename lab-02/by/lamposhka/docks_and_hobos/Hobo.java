package by.lamposhka.docks_and_hobos;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hobo implements Runnable {
    static final Random random = new Random();
    private final int stealing_time;
    private String cargo;
    Logger logger = Logger.getLogger("HobosLogger");

    public Hobo(int stealing_time) {
        this.stealing_time = stealing_time;
    }

    public void setCargo(String cargo) {
        synchronized (this) {
            this.cargo = cargo;
            System.out.println(this.cargo);
        }
    }

    public synchronized boolean isBusy() {
        return this.cargo != null;
    }

    @Override
    public void run() {
        while (true) {
                if (cargo == null) {
                    continue;
                }
                synchronized (Controller.getInstance()) {
                    for (Dock dock : Controller.getInstance().getDocks()) {
                        if (dock.steal(cargo)) {
                            try {
                                Thread.sleep(stealing_time * 1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            logger.log(Level.INFO, "successfully stolen");
                            cargo = null;
                            break;
                        }
                    }
                }

        }
    }
}
