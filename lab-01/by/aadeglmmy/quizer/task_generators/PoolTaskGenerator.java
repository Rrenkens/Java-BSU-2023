package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.TaskGenerator;
import by.aadeglmmy.quizer.tasks.TextTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {

  private final Collection<TextTask> tasks;
  private final Random random = new Random();
  private final boolean allowDuplicate;

  public PoolTaskGenerator(boolean allowDuplicate, TextTask... tasks) {
    this.tasks = new ArrayList<>(Arrays.asList(tasks));
    this.allowDuplicate = allowDuplicate;

    if (this.tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
  }

  public PoolTaskGenerator(boolean allowDuplicate, Collection<TextTask> tasks) {
    if (tasks.isEmpty()) {
      throw new NoSuchElementException("No tasks available in the pool.");
    }
    this.tasks = tasks;
    this.allowDuplicate = allowDuplicate;
  }

  @Override
  public TextTask generate() {
    if (tasks.isEmpty()) {
      throw new IllegalStateException("No tasks available in the pool.");
    }

    int randomIndex = random.nextInt(tasks.size());
    TextTask task = tasks.stream().skip(randomIndex).findFirst().orElse(null);

    if (!allowDuplicate) {
      tasks.remove(task);
    }

    return task;
  }

  public boolean getAllowDuplicate() {
    return allowDuplicate;
  }

  public Collection<TextTask> getTasks() {
    return this.tasks;
  }
}
