package by.aadeglmmy.docks_and_hobos.main_actors;

public class Ship {
  private int capacity;
  private final int cargoTypeIndex;

  public Ship(int capacity, int cargoTypeIndex) {
    this.capacity = capacity;
    this.cargoTypeIndex = cargoTypeIndex;
  }

  public int getCapacity() {
    return capacity;
  }

  public void decrementCapacity() {
    --capacity;
  }

  public int getCargoTypeIndex() {
    return cargoTypeIndex;
  }
}
