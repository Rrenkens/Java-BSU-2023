package by.aadeglmmy.docks_and_hobos.main_actors.hobos;

import by.aadeglmmy.docks_and_hobos.main_actors.docks.Dock;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hobos extends Thread {

  private final int stealingTime;
  private final int[] ingredientsCount;
  private final Dock[] docks;
  private final int eatingTime;
  private final Lock stealingLock = new ReentrantLock();
  private final Condition ingredientsStolen = stealingLock.newCondition();
  private final Condition hobosAreAssembled = stealingLock.newCondition();
  Hobo[] hobos;
  private int[] stolenIngredients;
  private int count;

  public Hobos(int quantity, int stealingTime, int[] ingredientsCount, int numOfCargoTypes,
      Dock[] docks, int eatingTime) {
    this.stealingTime = stealingTime;
    this.ingredientsCount = ingredientsCount;
    this.docks = docks;
    this.eatingTime = eatingTime;
    hobos = createHobos(quantity, numOfCargoTypes, this);
  }

  @Override
  public void run() {
    Random random = new Random();
    int hobosLength = hobos.length;
    for (Hobo hobo : hobos) {
      hobo.start();
    }
    while (true) {
      stolenIngredients = updateStolenIngredients();
      int cook1Index = random.nextInt(hobosLength);
      int cook2Index;
      do {
        cook2Index = random.nextInt(hobosLength);
      } while (cook2Index == cook1Index);
      Hobo cook1 = hobos[cook1Index];
      Hobo cook2 = hobos[cook2Index];
      for (Hobo hobo : hobos) {
        if (hobo != cook1 && hobo != cook2) {
          hobo.startRunning();
        }
      }
      stealingLock.lock();
      try {
        while (!Arrays.equals(ingredientsCount, stolenIngredients)) {
          try {
            ingredientsStolen.await();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      } finally {
        stealingLock.unlock();
      }
      count = 2;
      for (Hobo hobo : hobos) {
        hobo.stopRunning();
      }
      stealingLock.lock();
      try {
        while (count != hobosLength) {
          try {
            hobosAreAssembled.await();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      } finally {
        stealingLock.unlock();
      }
      try {
//        System.out.println("Hobos are eating...");
        Thread.sleep(eatingTime * 1000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public int[] getIngredientsCount() {
    return ingredientsCount;
  }

  private int[] updateStolenIngredients() {
    return new int[ingredientsCount.length];
  }

  public int[] getStolenIngredients() {
    return stolenIngredients;
  }

  public void incrementStolenIngredients(int cargoTypeIndex) {
    stealingLock.lock();
    try {
      ++stolenIngredients[cargoTypeIndex];
      ingredientsStolen.signal();
    } finally {
      stealingLock.unlock();
    }
  }

  public Hobo[] createHobos(int quantity, int numOfCargoTypes, Hobos hobos) {
    Hobo[] hoboArray = new Hobo[quantity];
    Random random = new Random();
    for (int i = 0; i < quantity; ++i) {
      int dockIndex = random.nextInt(docks.length);
      int cargoTypeIndex = i % numOfCargoTypes;
      hoboArray[i] = new Hobo(cargoTypeIndex, docks, numOfCargoTypes, dockIndex, hobos,
          stealingTime);
    }
    return hoboArray;
  }

  public void signalToHobos() {
    stealingLock.lock();
    try {
      hobosAreAssembled.signal();
      ++count;
    } finally {
      stealingLock.unlock();
    }
  }
}
