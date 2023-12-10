package by.mnik0_0.docks_and_hobos;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Dock implements Runnable {
    private final int unloadingSpeed;
    private final int dockCapacity;
    private final HashMap<String, Integer> goods = new HashMap<>();
    private Tunnel tunnel;
    private String name;

    public String getName() {
        return name;
    }

    public Dock(int unloadingSpeed, int dockCapacity, Tunnel tunnel, String name, ArrayList<String> types) {
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
        this.tunnel = tunnel;
        this.name = name;
        for (String type: types) {
            goods.put(type, 0);
        }
    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public int getDockCapacity() {
        return dockCapacity;
    }

    public synchronized boolean getGoodByType(String type) {
        int count = goods.get(type);
        if (count == 0) {
            return false;
        }
        goods.put(type, count - 1);
        return true;
    }

    @Override
    public void run() {
        while (true) {
            Ship ship = tunnel.getShipFromTunnel(this);

            if (ship == null) {
                continue;
            }

            double timeInSeconds = ship.getCapacity() / (double) unloadingSpeed;
            try {
                Thread.sleep((long) (timeInSeconds * 1000L));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int capacity = ship.getCapacity();
            String type = ship.getCargoType();
            synchronized (goods) {
                int capacityInDocks = goods.get(type);
                int tmpResult = capacityInDocks + capacity;
                int result = min(tmpResult, dockCapacity);
                int delta = tmpResult - result;
                if (delta > 0) {
                    System.out.printf("We lose in %s - %s %d%n", name, type, delta);
                }
                goods.put(type, result);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("------------------------\n");
                stringBuilder.append(name).append("\n");
                for (String t: goods.keySet()) {
                    stringBuilder.append(t).append(" ").append(goods.get(t)).append("\n");
                }
                stringBuilder.append("------------------------\n");
                System.out.println(stringBuilder);
            }
        }
    }
}
