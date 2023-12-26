package by.aadeglmmy.docks_and_hobos.main_actors;

import java.util.Random;

public class ShipGenerator extends Thread {

  private final int shipCapacityMin;
  private final int shipCapacityMax;
  private final Tunnel tunnel;
  private final int generatingTime;
  private final String[] cargoTypes;
  Random random = new Random();

  public ShipGenerator(Tunnel tunnel, int generatingTime, int shipCapacityMin, int shipCapacityMax,
      String[] cargoTypes) {
    this.tunnel = tunnel;
    this.generatingTime = generatingTime;
    this.shipCapacityMin = shipCapacityMin;
    this.shipCapacityMax = shipCapacityMax;
    this.cargoTypes = cargoTypes;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(generatingTime * 1000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      Ship ship = generateShip();
      System.out.println(
          ship + "was generated with capacity " + ship.getCapacity() + " and number of cargo "
              + ship.getCargoTypeIndex());
      tunnel.enterShip(ship);
    }
  }

  private Ship generateShip() {
    int capacity = random.nextInt(shipCapacityMax - shipCapacityMin + 1) + shipCapacityMin;
    int cargoTypeIndex = random.nextInt(cargoTypes.length);
    return new Ship(capacity, cargoTypeIndex);
  }
}
