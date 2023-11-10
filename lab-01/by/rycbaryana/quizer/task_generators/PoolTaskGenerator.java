package by.rycbaryana.quizer.task_generators;

import by.rycbaryana.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {
    ArrayList<Task> tasks;
    boolean allowDuplicate;
    Random random = new Random();
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = (ArrayList<Task>) tasks;
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new IndexOutOfBoundsException("No tasks left");
        }
        Task task = tasks.get(random.nextInt(0, tasks.size()));
        if (!allowDuplicate) {
            tasks.remove(task);
        }
        return task;
    }
}