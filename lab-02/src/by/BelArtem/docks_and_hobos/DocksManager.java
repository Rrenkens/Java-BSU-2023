package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;

public class DocksManager implements Runnable{

    private final List<Dock> dockList;

    public DocksManager(List<Dock> dockList) {
        this.dockList = dockList;
    }

    @Override
    public void run() {
        ArrayList<Thread> threads = new ArrayList<>();
        int size = dockList.size();
        for (int i = 0; i < size; ++i) {
            threads.add(new Thread(dockList.get(i)));
            threads.get(i).start();
        }
    }
}
