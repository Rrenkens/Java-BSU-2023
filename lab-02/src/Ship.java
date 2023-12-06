import java.util.Arrays;
import java.util.Objects;

public class Ship {
    private static double ship_capacity_min = 0;
    private static double ship_capacity_max = 500;

    private static int ID = 0;
    private int ID_;

    final Object lock = new Object();

    private double capacity = ship_capacity_min;
    private final int cargo_type;
    static final String[] cargo_types = {"1", "2", "3"};

    Ship(int cargo_type) {
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

    Ship(int capacity, int cargo_type) {
        this(cargo_type);
        if (capacity < ship_capacity_min || capacity > ship_capacity_max) {
            throw new IllegalArgumentException("You enter incorrect amount of capacity");
        }
        this.capacity = capacity;
    }
    Ship(int capacity, String cargo_type) {
        this(cargo_type);
        if (capacity < ship_capacity_min || capacity > ship_capacity_max) {
            throw new IllegalArgumentException("You enter incorrect amount of capacity");
        }
        this.capacity = capacity;
    }
    static void setShipCapacityMax(double max) {
        if (max - ship_capacity_min < -Main.eps) {
            throw new IllegalArgumentException("Max capacity can't be less than min capacity");
        }
        ship_capacity_max = max;
    }
    static void setShipCapacityMin(double min) {
        if (min < -Main.eps) {
            throw new IllegalArgumentException("Capacity can't be negative");
        }
        ship_capacity_min = min;
    }

    void setCapacity(double capacity, boolean isUnloaded) {
        if (capacity < (isUnloaded ? 0 : ship_capacity_min) - Main.eps) {
            throw new IllegalArgumentException("You enter incorrect capacity");
        }
        this.capacity = capacity;
    }
    double getShipCapacityMax() { return ship_capacity_max; }
    double getShipCapacityMin() { return ship_capacity_min; }

    int getCargoType() { return cargo_type; }
    int getID() { return ID; }

    double getCapacity() { return capacity; }

    @Override
    public String toString() {
        return "Ship: {\n" +
                "ID: " + ID_ + '\n' +
                "Type: " + cargo_types[cargo_type] + '\n' +
                "Capacity: " + capacity + '\n' +
                "}";
    }
}
