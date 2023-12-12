package by.busskov.quizer.task_generators;

import by.busskov.quizer.Task;
import by.busskov.quizer.exceptions.NoAvailableTasksException;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator {
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(List.of(tasks));
    }

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<? extends Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(tasks);
    }

    @Override
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new NoAvailableTasksException("PoolTaskGenerator out of tasks");
        }
        Random random = new Random();
        int index = random.nextInt(tasks.size());
        Task task = tasks.get(index);
        if (!allowDuplicate) {
            tasks.remove(index);
        }
        return task;
    }

    private final ArrayList<Task> tasks;
    private final boolean allowDuplicate;
}
