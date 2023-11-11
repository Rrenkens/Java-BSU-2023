package by.DashaGnedko.quizer.task_generators;

import by.DashaGnedko.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements Task.Generator {
    private boolean allowDuplicate;
    private ArrayList<Task> tasks;
    private Random random = new Random();
    PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(Arrays.stream(tasks).toList());
    }
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = (ArrayList<Task>) tasks;
    }

    public Task generate() {
        int index = random.nextInt(0, tasks.size());
        Task task = tasks.get(index);
        if (!allowDuplicate) {
            tasks.remove(index);
        }
        return task;
    }
}
