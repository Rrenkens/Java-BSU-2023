package by.katierevinska.docks_and_hobos;

import java.util.LinkedList;

public class Tunnel {
    private Long max_ships = 0L;
    private LinkedList<Ship> ships;

    Tunnel(){
        this.ships = new LinkedList<>();
    }
    boolean isFull(){
        return ships.size()>max_ships;
    }
    int sizeOfShips(){
        return ships.size();
    }
    boolean isEmpty(){
        return ships.isEmpty();
    }
    public void setMaxShips(Long count){
        max_ships = count;
    };
    public void setShip(Ship ship){
        ships.add(ship);
    }
    public void sinkShip(){
        ships.remove(ships.size()-1);
    }
    public Ship sendToDock(){
        return ships.remove();
    }
}
