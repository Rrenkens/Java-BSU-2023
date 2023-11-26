package by.BelArtem.docks_and_hobos;

public class Docks {

    //TODO: write this class properly

    private final int unloadingSpeed;
    private final int dockCapacity;

    public Docks (int unloadingSpeed, int dockCapacity) {
        this.unloadingSpeed = unloadingSpeed;
        this.dockCapacity = dockCapacity;
    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public int getDockCapacity() {
        return dockCapacity;
    }
}
