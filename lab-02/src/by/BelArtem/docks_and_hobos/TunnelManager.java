package by.BelArtem.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;

public class TunnelManager implements Runnable{
    private final Tunnel tunnel;

    private final ShipGenerator shipGenerator;

    private final List<Ship> ships;

    public TunnelManager(Tunnel tunnel, ShipGenerator shipGenerator) {
        this.tunnel = tunnel;
        this.shipGenerator = shipGenerator;

        this.ships = shipGenerator.getShips();
    }

    @Override
    public void run () {
        //List<Ship> ships = shipGenerator.getShips();
        while (true) {
            synchronized (ships){
                System.out.println("Entering sync block");

                if (tunnel.isFull()){
                    //ships = ships.subList(0, tunnel.getMaxShips());
                    while (ships.size() > tunnel.getMaxShips()) {
                        ships.remove(ships.size() - 1);
                    }
                } else {
                    if (ships.size() <= tunnel.getMaxShips()){
                        tunnel.setShipsInTunnel(ships.size());
                    } else {
                        tunnel.setShipsInTunnel(tunnel.getMaxShips());
                        //ships = ships.subList(0, tunnel.getMaxShips());
                        while (ships.size() > tunnel.getMaxShips()) {
                            ships.remove(ships.size() - 1);
                        }
                    }

                }

//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    System.out.println("Error");
//                    throw new RuntimeException(e);
//                }
//                System.out.println("Leaving sync block");
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("Error");
                throw new RuntimeException(e);
            }
        }
    }


    public Ship getFirstShip() {
        if (ships.isEmpty()) {
            return null;
        }
        Ship ship;
        synchronized (ships) {
            ship = ships.get(0);
            ships.remove(0);
            this.tunnel.removeShips(1);
        }
        return ship;
    }















//        List<Ship> ships = shipGenerator.getShips();
//        while (true) {
//            int size = ships.size();
//            int diff = size - tunnel.getShipsInTunnel();
//            int curShipsNumber = tunnel.getShipsInTunnel();
//            int max = tunnel.getMaxShips();
//            if (tunnel.isFull()){
//                    ships = ships.subList(0, max);
//            } else if ( )
//
////            else {
////                while (!tunnel.isFull() && tunnel.getShipsInTunnel() != ships.size()){
////
////                }
////            }
//
//
//
//
//
//            try {
//                Thread.sleep(shipGenerator.getGeneratingTime() * 500L);
//            } catch (InterruptedException e) {
//                System.out.println("During tunnel managing something went wrong");
//                throw new RuntimeException(e);
//            }
//        }
 //   }
}
