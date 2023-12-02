package by.lamposhka.docks_and_hobos;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Bay {
    private Tunnel tunnel = new Tunnel();
    private final ArrayList<Dock> docks = new ArrayList<>(ProgramArguments.DOCK_COUNT);
    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(ProgramArguments.DOCK_COUNT);
    void enterBay(Ship ship) {
        tunnel.add(ship);
    }
}
