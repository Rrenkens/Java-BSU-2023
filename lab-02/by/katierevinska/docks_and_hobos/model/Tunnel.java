package by.katierevinska.docks_and_hobos.model;

import java.util.LinkedList;

class Tunnel {
    private Long MAX_COUNT_OF_SHIPS = 0L;
    private LinkedList<Ship> ships;

    public Tunnel() {
        this.ships = new LinkedList<>();
    }

    private boolean isFull() {
        return ships.size() >= MAX_COUNT_OF_SHIPS;
    }

    int sizeOfShips() {
        return ships.size();
    }

    boolean isEmpty() {
        return ships.isEmpty();
    }

    public void setMaxShips(Long count) {
        MAX_COUNT_OF_SHIPS = count;
    }

    public synchronized void setShip(Ship ship) {
        System.out.println("Try set ship "+ship.getShipId()+" in tunnel if it not already full");
        if (this.isFull()) {
              this.sinkShip();
        }else {
            ships.add(ship);
            notify();
            System.out.println("Adding ship "+ship.getShipId()+" in tunnel, now " + sizeOfShips() + " ships in it");
        }
    }

    private void sinkShip() {
        System.out.println("Sink ship");
    }

    public synchronized Ship sendToDock() throws InterruptedException {
        System.out.println("Trying send ship to dock, wait if tunnel empty");
        while (this.isEmpty()) {
            wait();
        }
        Ship ship = ships.remove();
        System.out.println("Now can send ship "+ship.getShipId()+" to dock");
        return ship;
    }
}
