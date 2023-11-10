package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.Task;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator {
    private Set<Task> tasksSet = new HashSet<>();
    private List<Task> tasksList = new ArrayList<>();
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        if (tasks.length == 0) throw new IllegalArgumentException("You can't provide empty task list");
        for (Task task : tasks) {
            if (allowDuplicate || !tasksSet.contains(task)) {
                tasksSet.add(task);
                tasksList.add(task);
            }
        }
    }
    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        if (tasks.isEmpty()) throw new IllegalArgumentException("You can't provide empty task list");
        for (Task task : tasks) {
            if (allowDuplicate || !tasksSet.contains(task)) {
                tasksSet.add(task);
                tasksList.add(task);
            }
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        return tasksList.get((int) (Math.random() * tasksList.size()));
    }
}