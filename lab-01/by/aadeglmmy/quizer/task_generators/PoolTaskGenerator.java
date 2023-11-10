package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.Task;
import by.aadeglmmy.quizer.TaskGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {

  private final Collection<Task> tasks;
  private final Random random = new Random();
  private final boolean allowDuplicate;
  private final List<Integer> availableIndexes = new ArrayList<>();

  public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
    this.tasks = new ArrayList<>(Arrays.asList(tasks));
    this.allowDuplicate = allowDuplicate;

    if (this.tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
  }

  public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
    if (tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
    this.tasks = tasks;
    this.allowDuplicate = allowDuplicate;
  }

  public void updateAvailableIndexes() {
    if (tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
    if (!allowDuplicate) {
      availableIndexes.clear();
      for (int i = 0; i < tasks.size(); ++i) {
        availableIndexes.add(i);
      }
    }
  }

  @Override
  public Task generate() {
    int taskIndex;
    if (allowDuplicate) {
      taskIndex = random.nextInt(tasks.size());
    } else {
      if (availableIndexes.isEmpty()) {
        throw new IllegalStateException("No tasks available in the pool.");
      }

      int randomIndex = random.nextInt(availableIndexes.size());
      taskIndex = availableIndexes.get(randomIndex);

      availableIndexes.remove(randomIndex);
    }

    return tasks.stream().skip(taskIndex).findFirst().orElse(null);
  }

  public boolean getAllowDuplicate() {
    return allowDuplicate;
  }

  public Collection<Task> getTasks() {
    return this.tasks;
  }
}
