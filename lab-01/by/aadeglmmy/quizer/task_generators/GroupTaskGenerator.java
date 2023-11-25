package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.Task;
import by.aadeglmmy.quizer.exceptions.GroupsNotUniqueException;
import by.aadeglmmy.quizer.exceptions.PoolsNotUniqueException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class GroupTaskGenerator implements Task.Generator {

  private final Collection<Task.Generator> generators;
  private final Random random = new Random();
  private final Collection<Task.Generator> availableElements = new ArrayList<>();

  public GroupTaskGenerator(Task.Generator... generators) {
    this.generators = new ArrayList<>(Arrays.asList(generators));
    if (this.generators.isEmpty()) {
      throw new NoSuchElementException("No generators available in the group");
    }

    arePoolsAndGroupsUnique();
  }

  public GroupTaskGenerator(Collection<Task.Generator> generators) {
    if (generators.isEmpty()) {
      throw new NoSuchElementException("No generators available in the group");
    }
    this.generators = generators;

    arePoolsAndGroupsUnique();
  }

  public void updateAvailableElements() {
    availableElements.clear();
    availableElements.addAll(generators);
    for (Task.Generator generator : generators) {
      if (generator instanceof PoolTaskGenerator) {
        ((PoolTaskGenerator) generator).updateAvailableElements();
      } else if (generator instanceof GroupTaskGenerator) {
        ((GroupTaskGenerator) generator).updateAvailableElements();
      }
    }
  }

  @Override
  public Task generate() {
    while (!availableElements.isEmpty()) {
      int randomIndex = random.nextInt(availableElements.size());
      Task.Generator generator = availableElements.stream().skip(randomIndex).findFirst()
          .orElse(null);

      try {
        assert generator != null;
        return generator.generate();
      } catch (Exception e) {
        availableElements.remove(generator);
      }
    }

    throw new IllegalStateException("All generators failed to generate a task");
  }

  private void arePoolsAndGroupsUnique() {
    Set<Task.Generator> taskGenerators = new HashSet<>();
    Set<Collection<Task>> poolCollections = new HashSet<>();
    Set<Collection<Task.Generator>> groupCollections = new HashSet<>();
    for (Task.Generator generator : generators) {
      if (generator instanceof PoolTaskGenerator) {
        if (!taskGenerators.add(generator)) {
          throw new PoolsNotUniqueException("Putting identical poolTaskGenerators into the group");
        }
        if (!poolCollections.add(((PoolTaskGenerator) generator).getTasks())) {
          throw new PoolsNotUniqueException(
              "Putting poolTaskGenerators with identical collections of tasks");
        }
      } else if (generator instanceof GroupTaskGenerator) {
        if (!taskGenerators.add(generator)) {
          throw new GroupsNotUniqueException(
              "Putting identical groupTaskGenerators into the group");
        }
        if (!groupCollections.add(((GroupTaskGenerator) generator).getGenerators())) {
          throw new GroupsNotUniqueException(
              "Putting groupTaskGenerators with identical collections of taskGenerators");
        }
      }
    }
  }

  public Collection<Task.Generator> getGenerators() {
    return generators;
  }
}
