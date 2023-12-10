import java.util.Arrays;
import java.util.Objects;

public class Ship {
    static long ship_capacity_min = 0;
    static long ship_capacity_max = 500;

    static long ID = 0;
    long ID_;

    final Object lock = new Object();

    long capacity = ship_capacity_min;
    final long cargo_type;
    static String[] cargo_types;

    Ship(long cargo_type) {
        if (cargo_type >= cargo_types.length) {
            throw new IllegalArgumentException("Index bound error");
        }
        this.cargo_type = cargo_type;
        ID_ = ++ID;
    }
    Ship(String cargo_type) {
        for (int i = 0; i < cargo_types.length; ++i) {
            if (cargo_types[i].equals(cargo_type)) {
                this.cargo_type = i;
                ID_ = ++ID;
                return;
            }
        }
        throw new IllegalArgumentException("Unexist type");
    }

    Ship(long capacity, long cargo_type) {
        this(cargo_type);
        if (capacity < ship_capacity_min || capacity > ship_capacity_max) {
            throw new IllegalArgumentException("You enter incorrect amount of capacity");
        }
        this.capacity = capacity;
    }
    Ship(long capacity, String cargo_type) {
        this(cargo_type);
        if (capacity < ship_capacity_min || capacity > ship_capacity_max) {
            throw new IllegalArgumentException("You enter incorrect amount of capacity");
        }
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Ship: {" +
                " ID: " + ID_ +
                " Type: " + cargo_types[(int) cargo_type] +
                " Capacity: " + capacity +
                "}";
    }
}

//    static void setShipCapacityMin(double min) {
//        if (min < -Main.eps) {
//            throw new IllegalArgumentException("Capacity can't be negative");
//        }
//        ship_capacity_min = min;
//    }
//
//    void setCapacity(double capacity, boolean isUnloaded) {
//        if (capacity < (isUnloaded ? 0 : ship_capacity_min) - Main.eps) {
//            throw new IllegalArgumentException("You enter incorrect capacity");
//        }
//        this.capacity = capacity;
//    }
//    double getShipCapacityMax() { return ship_capacity_max; }
//    double getShipCapacityMin() { return ship_capacity_min; }
//
//    long getCargoType() { return cargo_type; }
//    long getID() { return ID; }
//
//    double getCapacity() { return capacity; }
//static void setShipCapacityMax(long max) {
//    if (max - ship_capacity_min < -Main.eps) {
//        throw new IllegalArgumentException("Max capacity can't be less than min capacity");
//    }
//    ship_capacity_max = max;
//}
