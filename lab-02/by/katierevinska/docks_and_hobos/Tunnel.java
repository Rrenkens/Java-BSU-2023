package by.katierevinska.docks_and_hobos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tunnel {
    private int max_ships;
    private LinkedList<Ship> ships;

    Tunnel(){
        this.ships = new LinkedList<>() {
        };
    }
    public void setShip(Ship ship){
        ships.add(ship);
    }
    public Ship deleteShip(){
        return ships.peek();
    }
}
