package by.mnik0_0.docks_and_hobos;

import java.util.ArrayList;
import java.util.Random;

public class ShipGenerator implements Runnable {
    private int generatingTime;
    private int shipCapacityMin;
    private int shipCapacityMax;
    private Tunnel tunnel;
    private final ArrayList<String> cargoTypes;
    private final Random random = new Random();

    public ShipGenerator(int generatingTime, int shipCapacityMin, int shipCapacityMax, ArrayList<String> cargoTypes, Tunnel tunnel) {
        this.generatingTime = generatingTime;
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
        this.cargoTypes = cargoTypes;
        this.tunnel = tunnel;
    }

    @Override
    public void run() {
        int name = 0;
        while (true) {
            int capacity = random.nextInt(shipCapacityMax - shipCapacityMin + 1) + shipCapacityMin;

            int cargoId = random.nextInt(cargoTypes.size());

            Ship ship = new Ship(
                    capacity,
                    cargoTypes.get(cargoId),
                    String.valueOf(name)
            );
            name++;
            tunnel.enterTunnel(ship);
            try {
                Thread.sleep(generatingTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
