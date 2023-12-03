package by.waitingsolong.docks_and_hobos;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CargoType {
    private static Random random = new Random();
    private static List<String> types;

    public static void setTypes(List<String> types_) {
        types = types_;

    }
    private int id;

    public CargoType(int id) {
        if (id < 0 || id >= types.size()) {
            throw new IllegalArgumentException("Invalid CargoType id: " + id);
        }
        this.id = id;
    }

    public String getName() {
        return types.get(id);
    }

    public static CargoType getRandom() {
        return new CargoType(random.nextInt(types.size()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CargoType cargoType = (CargoType) obj;
        return id == cargoType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
