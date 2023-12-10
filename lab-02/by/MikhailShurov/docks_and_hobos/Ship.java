package by.MikhailShurov.docks_and_hobos;

import javax.swing.*;

public class Ship {
    private double capacity_;
    private String cargo_type_;
    public  Ship(double capacity, String cargo_type) {
        capacity_ = capacity;
        cargo_type_ = cargo_type;
    }

    double getCapacity() {
        return capacity_;
    }
    String getCargoType() {return cargo_type_;}

    double unload(double unloadAmount) {
        if (capacity_ - unloadAmount >= 0 ) {
            capacity_ -= unloadAmount;
            return unloadAmount;
        } else {
            double tmp = capacity_;
            capacity_ = 0;
            return tmp;
        }
    }
}
