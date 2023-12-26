package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class PoolTaskGenerator implements Task.Generator {

  private final ArrayList<Task> tasks;
  private final Random random = new Random();
  private final boolean allowDuplicate;

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
    this.tasks = new ArrayList<>();
    this.tasks.addAll(tasks);
    this.allowDuplicate = allowDuplicate;
  }

  @Override
  public Task generate() {
    Task task;
    if (allowDuplicate) {
      int taskIndex = random.nextInt(tasks.size());
      task = tasks.get(taskIndex);
    } else {
      if (tasks.isEmpty()) {
        throw new IllegalStateException("No tasks available in the pool.");
      }
      int taskIndex = random.nextInt(tasks.size());
      task = tasks.get(taskIndex);
      tasks.remove(task);
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
