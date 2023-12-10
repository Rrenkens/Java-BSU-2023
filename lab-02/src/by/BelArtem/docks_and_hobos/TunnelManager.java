package by.BelArtem.docks_and_hobos;

import java.util.List;

public class TunnelManager implements Runnable{
    private final Tunnel tunnel;
    private final List<Ship> ships;

    public TunnelManager(Tunnel tunnel, ShipGenerator shipGenerator) {
        this.tunnel = tunnel;
        this.ships = shipGenerator.getShips();
    }

    @Override
    public void run () {
        while (true) {
            synchronized (ships){
                this.updateTunnel();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error");
                throw new RuntimeException(e);
            }
        }
    }

    public Ship getFirstShip() {
        Ship ship;
        synchronized (ships) {
            this.updateTunnel();
            if (ships.isEmpty()) {
                return null;
            }
            ship = ships.get(0);
            ships.remove(0);
            this.tunnel.removeShips(1);
        }
        return ship;
    }

    public void updateTunnel () {
        if (tunnel.isFull()){
            while (ships.size() > tunnel.getMaxShips()) {
                ships.remove(ships.size() - 1);
            }
        } else {
            if (ships.size() <= tunnel.getMaxShips()){
                tunnel.setShipsInTunnel(ships.size());
            } else {
                tunnel.setShipsInTunnel(tunnel.getMaxShips());
                while (ships.size() > tunnel.getMaxShips()) {
                    ships.remove(ships.size() - 1);
                }
            }

        }
    }
}
