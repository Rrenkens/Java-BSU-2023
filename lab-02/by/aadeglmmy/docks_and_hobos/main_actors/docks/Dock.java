package by.aadeglmmy.docks_and_hobos.main_actors.docks;

import by.aadeglmmy.docks_and_hobos.main_actors.Ship;
import by.aadeglmmy.docks_and_hobos.main_actors.Tunnel;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dock extends Thread {

  private final int unloadingSpeed;
  private final int[] dockCapacities;
  private final int[] dockFullness;
  private final Tunnel tunnel;
  private final Lock dockLock = new ReentrantLock();
  private final Condition dockNotFull = dockLock.newCondition();


  public Dock(int unloadingSpeed, int[] dockCapacity, int[] dockFullness, Tunnel tunnel) {
    this.unloadingSpeed = unloadingSpeed;
    this.dockCapacities = dockCapacity;
    this.dockFullness = dockFullness;
    this.tunnel = tunnel;
  }

  @Override
  public void run() {
    while (true) {
      Ship ship = tunnel.getNextShip();
//      System.out.println(
//          "Dock " + this + " has gotten a ship " + ship + " with capacity " + ship.getCapacity()
//              + " and cargo " + ship.getCargoTypeIndex());
      int cargoTypeIndex = ship.getCargoTypeIndex();
      int capacity = dockCapacities[cargoTypeIndex];
      while (ship.getCapacity() > 0) {
        dockLock.lock();
        try {
        while (dockFullness[cargoTypeIndex] >= capacity) {
          try {
            dockNotFull.await();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        try {
          Thread.sleep(unloadingSpeed * 1000L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        ship.decrementCapacity();
        ++dockFullness[cargoTypeIndex];
//        System.out.println("Ship: " + ship + " has lost in dock " + this + " cargo type "
//            + ship.getCargoTypeIndex());
      } finally {
          dockLock.unlock();
        }
      }
    }
  }

  public int[] getDockFullness() {
    return dockFullness;
  }

  public void decrementDockFullness(int cargoTypeIndex) {
    dockLock.lock();
    try {
      --dockFullness[cargoTypeIndex];
      dockNotFull.signal();
    } finally {
      dockLock.unlock();
    }
  }
}
