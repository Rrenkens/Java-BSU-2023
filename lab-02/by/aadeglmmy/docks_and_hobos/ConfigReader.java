package by.aadeglmmy.docks_and_hobos;

import by.aadeglmmy.docks_and_hobos.exceptions.ConfigIOException;
import by.aadeglmmy.docks_and_hobos.exceptions.InvalidConfigException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {

  private int generatingTime;
  private int shipCapacityMin;
  private int shipCapacityMax;
  private String[] cargoTypes;
  private int maxShips;
  private int docks;
  private int unloadingSpeed;
  private int[] dockCapacity;
  private int hobos;
  private int[] ingredientsCount;
  private int stealingTime;
  private int eatingTime;

  public ConfigReader(String filePath) {
    try (FileReader reader = new FileReader(filePath)) {
      Gson gson = new GsonBuilder().create();
      ConfigReader config = gson.fromJson(reader, ConfigReader.class);
      validateConfig(config);
      copyValues(config);
    } catch (IOException e) {
      throw new ConfigIOException("Error reading the configuration file", e);
    }
  }

  private void validateConfig(ConfigReader config) {
    if (config.generatingTime <= 0) {
      throw new InvalidConfigException("generatingTime must be a positive number.");
    }
    if (config.shipCapacityMin <= 0) {
      throw new InvalidConfigException("shipCapacityMin must be a positive number");
    }
    if (config.shipCapacityMax < config.shipCapacityMin) {
      throw new InvalidConfigException("shipCapacityMax cannot be less than shipCapacityMin");
    }
    if (config.cargoTypes == null || config.cargoTypes.length == 0) {
      throw new InvalidConfigException("cargoTypes must be a non-empty array");
    }
    if (config.maxShips <= 0) {
      throw new InvalidConfigException("maxShips must be a positive number");
    }
    if (config.docks <= 0) {
      throw new InvalidConfigException("docks must be a positive number");
    }
    if (config.unloadingSpeed <= 0) {
      throw new InvalidConfigException("unloadingSpeed must be a positive number");
    }
    if (config.dockCapacity == null || config.dockCapacity.length == 0) {
      throw new InvalidConfigException("dockCapacity must be a non-empty array");
    }
    if (config.dockCapacity.length != config.cargoTypes.length) {
      throw new InvalidConfigException(
          "The size of dockCapacity must be equal to the size of cargoTypes");
    }
    for (int capacity : config.dockCapacity) {
      if (capacity <= 0) {
        throw new InvalidConfigException("Numbers in dockCapacity must be positive");
      }
    }
    if (config.hobos <= 2) {
      throw new InvalidConfigException("hobos must be more than 2");
    }
    if (config.ingredientsCount == null || config.ingredientsCount.length == 0) {
      throw new InvalidConfigException("ingredientsCount must be a non-empty array");
    }
    if (config.ingredientsCount.length != config.cargoTypes.length) {
      throw new InvalidConfigException(
          "The size of ingredientsCount must be equal to the size of cargoTypes");
    }
    for (int ingredients : config.ingredientsCount) {
      if (ingredients <= 0) {
        throw new InvalidConfigException("Numbers in ingredientsCount must be positive");
      }
    }
    if (config.stealingTime <= 0) {
      throw new InvalidConfigException("stealingTime must be a positive number");
    }
    if (config.eatingTime <= 0) {
      throw new InvalidConfigException("eatingTime must be a positive number");
    }
  }

  private void copyValues(ConfigReader config) {
    this.generatingTime = config.generatingTime;
    this.shipCapacityMin = config.shipCapacityMin;
    this.shipCapacityMax = config.shipCapacityMax;
    this.cargoTypes = config.cargoTypes;
    this.maxShips = config.maxShips;
    this.docks = config.docks;
    this.unloadingSpeed = config.unloadingSpeed;
    this.dockCapacity = config.dockCapacity;
    this.hobos = config.hobos;
    this.ingredientsCount = config.ingredientsCount;
    this.stealingTime = config.eatingTime;
    this.eatingTime = config.eatingTime;
  }

  public int getGeneratingTime() {
    return generatingTime;
  }

  public int getShipCapacityMin() {
    return shipCapacityMin;
  }

  public int getShipCapacityMax() {
    return shipCapacityMax;
  }

  public String[] getCargoTypes() {
    return cargoTypes;
  }

  public int getMaxShips() {
    return maxShips;
  }

  public int getDocks() {
    return docks;
  }

  public int getUnloadingSpeed() {
    return unloadingSpeed;
  }

  public int[] getDockCapacity() {
    return dockCapacity;
  }

  public int getHobos() {
    return hobos;
  }

  public int[] getIngredientsCount() {
    return ingredientsCount;
  }

  public int getStealingTime() {
    return stealingTime;
  }

  public int getEatingTime() {
    return eatingTime;
  }
}

