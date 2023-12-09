package by.katierevinska.docks_and_hobos.model;

import java.util.LinkedList;

class Tunnel {
    private Long max_ships = 0L;
    private LinkedList<Ship> ships;

    public Tunnel() {
        this.ships = new LinkedList<>();
    }

    private boolean isFull() {
        return ships.size() >= max_ships;
    }

    int sizeOfShips() {
        return ships.size();
    }

    boolean isEmpty() {
        return ships.isEmpty();
    }

    public void setMaxShips(Long count) {
        max_ships = count;
    }

    public synchronized void setShip(Ship ship) {
        System.out.println("Try set ship in tunnel if it not already full");
        if (this.isFull()) {
              this.sinkShip();
        }else {
            ships.add(ship);
            notify();
            System.out.println("Adding ship in tunnel, now " + sizeOfShips() + " ships in it");
        }
    }

    private void sinkShip() {
        System.out.println("Sink lastIn ship");
    }

    public synchronized Ship sendToDock() throws InterruptedException {
        System.out.println("Trying send ship to dock, wait if tunnel empty");
        while (this.isEmpty()) {
            wait();
        }
        System.out.println("Now can send ship to dock");
        return ships.remove();
    }
}
