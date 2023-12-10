package by.aadeglmmy.docks_and_hobos.main_actors.hobos;

import by.aadeglmmy.docks_and_hobos.main_actors.docks.Dock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hobo extends Thread {

  private final int cargoTypeIndex;
  private final Dock[] docks;
  private final int numOfCargoTypes;
  private final int dockIndex;
  private final Hobos hobos;
  private final int stealingTime;
  private final Lock runningLock = new ReentrantLock();
  private final Condition runningCondition = runningLock.newCondition();
  private volatile boolean running;


  public Hobo(int cargoTypeIndex, Dock[] docks, int numOfCargoTypes, int dockIndex, Hobos hobos,
      int stealingTime) {
    this.cargoTypeIndex = cargoTypeIndex;
    this.docks = docks;
    this.numOfCargoTypes = numOfCargoTypes;
    this.dockIndex = dockIndex;
    this.hobos = hobos;
    this.stealingTime = stealingTime;
  }

  @Override
  public void run() {
    int dockCounter = dockIndex;
    int cargoTypeCounter = cargoTypeIndex - 1;
    boolean breaker = false;
    while (true) {
      runningLock.lock();
      try {
        while (!running) {
          try {
            hobos.signalToHobos();
            runningCondition.await();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      } finally {
        runningLock.unlock();
      }
      while (running) {
        cargoTypeCounter = (cargoTypeCounter + 1) % numOfCargoTypes;
        do {
          while (true) {
            synchronized (hobos) {
              while (hobos.getStolenIngredients()[cargoTypeCounter]
                  == hobos.getIngredientsCount()[cargoTypeCounter]) {
                cargoTypeCounter = (cargoTypeCounter + 1) % numOfCargoTypes;
                if (cargoTypeCounter == cargoTypeIndex) {
                  breaker = true;
                  break;
                }
              }
              if (breaker) {
                breaker = false;
                break;
              }
              synchronized (docks[dockCounter]) {
                if (!(docks[dockCounter].getDockFullness()[cargoTypeCounter] != 0
                    && hobos.getStolenIngredients()[cargoTypeCounter]
                    < hobos.getIngredientsCount()[cargoTypeCounter])) {
                  break;
                }
                docks[dockCounter].decrementDockFullness(cargoTypeCounter);
                hobos.incrementStolenIngredients(cargoTypeCounter);
              }
            }
            try {
              Thread.sleep(stealingTime * 1000L);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
//            System.out.println("Hobo " + this + " stole from dock " + docks[dockCounter] + " cargo "
//                + cargoTypeCounter);
          }
          dockCounter = (dockCounter + 1) % docks.length;
        } while (dockCounter != dockIndex);
      }
    }
  }

  public void stopRunning() {
    running = false;
  }

  public void startRunning() {
    runningLock.lock();
    try {
      running = true;
      runningCondition.signal();
    } finally {
      runningLock.unlock();
    }
  }
}
