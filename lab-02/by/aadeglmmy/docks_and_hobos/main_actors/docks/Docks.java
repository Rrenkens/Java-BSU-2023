package by.aadeglmmy.docks_and_hobos.main_actors.docks;

import by.aadeglmmy.docks_and_hobos.main_actors.Tunnel;

public class Docks {
  private final int quantity;
  private final int unloadingSpeed;
  private final int numOfCargoTypes;
  private final Tunnel tunnel;
  private final int[] dockCapacity;

  public Docks(int quantity, int unloadingSpeed, int numOfCargoTypes, Tunnel tunnel, int[] dockCapacity) {
    this.quantity = quantity;
    this.unloadingSpeed = unloadingSpeed;
    this.numOfCargoTypes = numOfCargoTypes;
    this.tunnel = tunnel;
    this.dockCapacity = dockCapacity;
  }

  public Dock[] createDocks() {
    Dock[] docks = new Dock[quantity];
    int[] dockFullness = new int[numOfCargoTypes];
    for (int i = 0; i < quantity; ++i) {
      Dock dock = new Dock(unloadingSpeed, dockCapacity, dockFullness, tunnel);
      docks[i] = dock;
    }
    return docks;
  }

  public void startDocks(Dock[] docks) {
    for (Dock dock : docks) {
      dock.start();
    }
  }
}
