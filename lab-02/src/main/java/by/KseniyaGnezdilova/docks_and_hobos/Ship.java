package by.KseniyaGnezdilova.docks_and_hobos;

public class Ship {
    private int ship_capacity;
    private String cargo_type;
    public Ship(int ship_capacity, String cargo_type){
        this.ship_capacity = ship_capacity;
        this.cargo_type = cargo_type;
    }

    public void setShip_capacity(int ship_capacity){
        this.ship_capacity = ship_capacity;
    }
    public int getShip_capacity(){
        return ship_capacity;
    }

    public String getCargo_type(){
        return cargo_type;
    }
    @Override
    public String toString() {
        return "Ship{" +
                "ship_capacity=" + ship_capacity +
                ", cargo_type=" + cargo_type +
                '}';
    }

    public void writeShip(){
        System.out.println(toString());
    }
}
