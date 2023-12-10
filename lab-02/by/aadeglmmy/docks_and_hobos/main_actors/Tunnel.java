package by.aadeglmmy.docks_and_hobos.main_actors;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {

  private final int maxShips;
  private final Queue<Ship> shipsInTunnel = new LinkedList<>();
  private final Lock lock = new ReentrantLock();
  private final Condition shipAvailable = lock.newCondition();

  public Tunnel(int maxShips) {
    this.maxShips = maxShips;
  }

  public void enterShip(Ship ship) {
    lock.lock();
    try {
      if (shipsInTunnel.size() >= maxShips) {
//        System.out.println(
//            "Ship " + ship.getCargoTypeIndex() + " with capacity " + ship.getCapacity()
//                + " sank in the tunnel");
        return;
      }

      shipsInTunnel.add(ship);
      shipAvailable.signal();
//      System.out.println("Ship " + ship.getCargoTypeIndex() + " with capacity " + ship.getCapacity()
//          + " entered the tunnel");
    } finally {
      lock.unlock();
    }
  }

  public Ship getNextShip() {
    lock.lock();
    try {
      while (shipsInTunnel.isEmpty()) {
        try {
          shipAvailable.await();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      //      System.out.println(
//          ship + " with capacity " + ship.getCapacity() + " and cargo " + ship.getCargoTypeIndex()
//              + "was removed from the tunnel by dock.");
      return shipsInTunnel.poll();
    } finally {
      lock.unlock();
    }
  }
}
