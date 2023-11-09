package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.Task;
import by.aadeglmmy.quizer.TaskGenerator;
import by.aadeglmmy.quizer.exceptions.PoolsNotUniqueException;
import by.aadeglmmy.quizer.tasks.TextTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;


public class GroupTaskGenerator implements TaskGenerator {

  private final Collection<TaskGenerator> generators;
  private final Random random = new Random();

  public GroupTaskGenerator(TaskGenerator... generators) {
    this.generators = new ArrayList<>(Arrays.asList(generators));
    if (this.generators.isEmpty()) {
      throw new NoSuchElementException("No generators available in the group");
    }

    arePoolsUnique();
  }

  public GroupTaskGenerator(Collection<TaskGenerator> generators) {
    if (generators.isEmpty()) {
      throw new NoSuchElementException("No generators available in the group");
    }
    this.generators = generators;

    arePoolsUnique();
  }

  @Override
  public Task generate() {
    Collection<TaskGenerator> availableGenerators = generators;

    while (!availableGenerators.isEmpty()) {
      int randomIndex = random.nextInt(availableGenerators.size());
      TaskGenerator generator = generators.stream().skip(randomIndex).findFirst().orElse(null);

      try {
        assert generator != null;
        return generator.generate();
      } catch (Exception e) {
        availableGenerators.remove(generator);
      }
    }

    throw new IllegalStateException("All generators failed to generate a task");
  }

  private void arePoolsUnique() {
    Map<TaskGenerator, String> poolTaskGenerators = new HashMap<>();
    Map<Collection<TextTask>, String> collections = new HashMap<>();
    for (TaskGenerator generator : generators) {
      if (generator instanceof PoolTaskGenerator) {
        if (poolTaskGenerators.put(generator, "") != null) {
          throw new PoolsNotUniqueException("Putting identical poolTaskGenerators into the group");
        }
        if (collections.put(((PoolTaskGenerator) generator).getTasks(), "") != null) {
          throw new PoolsNotUniqueException(
              "Putting poolTaskGenerators with identical collections of textTasks");
        }
      }
    }
  }

  public Collection<TaskGenerator> getGenerators() {
    return generators;
  }
}
