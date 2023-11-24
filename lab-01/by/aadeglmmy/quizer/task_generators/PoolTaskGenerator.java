package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.Task;
import by.aadeglmmy.quizer.TaskGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {

  private final Collection<Task> tasks;
  private final Random random = new Random();
  private final boolean allowDuplicate;
  private final Collection<Task> availableElements = new ArrayList<>();

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

  public void updateAvailableElements() {
    if (tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
    if (!allowDuplicate) {
      availableElements.clear();
      availableElements.addAll(tasks);
    }
  }

  @Override
  public Task generate() {
    Task task;
    if (allowDuplicate) {
      int taskIndex = random.nextInt(tasks.size());
      task = tasks.stream().skip(taskIndex).findFirst().orElse(null);
    } else {
      if (availableElements.isEmpty()) {
        throw new IllegalStateException("No tasks available in the pool.");
      }
      int taskIndex = random.nextInt(availableElements.size());
      task = availableElements.stream().skip(taskIndex).findFirst().orElse(null);
      availableElements.remove(task);
    }

    return task;
  }

  public boolean getAllowDuplicate() {
    return allowDuplicate;
  }

  public Collection<Task> getTasks() {
    return this.tasks;
  }
}
