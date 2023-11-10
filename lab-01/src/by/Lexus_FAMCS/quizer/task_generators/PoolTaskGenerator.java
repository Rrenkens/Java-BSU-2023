package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.Task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PoolTaskGenerator {
    private Set<Task> tasks = new HashSet<>();
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        if (tasks.length == 0) throw new IllegalArgumentException("You can't provide empty task list");
        for (Task task : tasks) {
            if (allowDuplicate || !this.tasks.contains(task)) this.tasks.add(task);
        }
    }
    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        if (tasks.isEmpty()) throw new IllegalArgumentException("You can't provide empty taskGenerator list");
        for (Task task : tasks) {
            if (allowDuplicate || !this.tasks.contains(task)) this.tasks.add(task);
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        int num = (int) (Math.random() * tasks.size());
        return tasks.stream().toList().get(num);
    }
}