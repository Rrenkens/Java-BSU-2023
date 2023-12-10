package by.mnik0_0.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Tunnel {
    private final int maxShips;
    private Deque<Ship> shipsInTunnel;

    public Tunnel(int maxShips) {
        this.maxShips = maxShips;
        this.shipsInTunnel = new ArrayDeque<>();
    }

    public synchronized void enterTunnel(Ship ship) {
        if (maxShips <= shipsInTunnel.size()) {
            ship.sink();
            return;
        }
        System.out.printf("enter the tunnel %s%n", ship.getName());

        shipsInTunnel.add(ship);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("------------------------\n");
        stringBuilder.append("TUNNEL\n");
        for (Ship s: shipsInTunnel) {
            stringBuilder.append(s.getName()).append(" ").append(s.getCargoType()).append(" ").append(s.getCapacity()).append("\n");
        }
        stringBuilder.append("------------------------\n");
        System.out.println(stringBuilder);
    }

    public synchronized Ship getShipFromTunnel(Dock dock) {
        if (shipsInTunnel.isEmpty()) {
            return null;
        }
        Ship ship = shipsInTunnel.pop();
        System.out.printf("leave the tunnel %s%n", ship.getName());

        return ship;
    }
}
