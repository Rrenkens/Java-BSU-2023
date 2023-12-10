package by.aadeglmmy.docks_and_hobos;

import by.aadeglmmy.docks_and_hobos.main_actors.ShipGenerator;
import by.aadeglmmy.docks_and_hobos.main_actors.Tunnel;
import by.aadeglmmy.docks_and_hobos.main_actors.docks.Dock;
import by.aadeglmmy.docks_and_hobos.main_actors.docks.Docks;
import by.aadeglmmy.docks_and_hobos.main_actors.hobos.Hobos;

public class DocksAndHobos {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("You must specify the path to config.json in the program arguments.");
      return;
    }

    String configFilePath = args[0];
    ConfigReader configReader = new ConfigReader(configFilePath);

    int generatingTime = configReader.getGeneratingTime();
    int shipCapacityMin = configReader.getShipCapacityMin();
    int shipCapacityMax = configReader.getShipCapacityMax();
    String[] cargoTypes = configReader.getCargoTypes();
    int maxShips = configReader.getMaxShips();
    int dockQuantity = configReader.getDocks();
    int unloadingSpeed = configReader.getUnloadingSpeed();
    int[] dockCapacity = configReader.getDockCapacity();
    int hoboQuantity = configReader.getHobos();
    int[] ingredientsCount = configReader.getIngredientsCount();
    int stealingTime = configReader.getStealingTime();
    int eatingTime = configReader.getEatingTime();

    Tunnel tunnel = new Tunnel(maxShips);
    ShipGenerator shipGenerator = new ShipGenerator(tunnel, generatingTime, shipCapacityMin,
        shipCapacityMax, cargoTypes);
    Docks docks = new Docks(dockQuantity, unloadingSpeed, cargoTypes.length, tunnel, dockCapacity);
    Dock[] docks1 = docks.createDocks();
    Hobos hobos = new Hobos(hoboQuantity, stealingTime, ingredientsCount, cargoTypes.length, docks1,
        eatingTime);
    shipGenerator.start();
    docks.startDocks(docks1);
    hobos.start();
  }
}
