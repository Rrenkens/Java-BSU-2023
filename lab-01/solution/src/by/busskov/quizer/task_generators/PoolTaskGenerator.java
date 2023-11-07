package by.busskov.quizer.task_generators;

import by.busskov.quizer.Task;
import by.busskov.quizer.TaskGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.tasks = Arrays.copyOf(tasks, tasks.length);
    }

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        Object[] array = tasks.toArray();
        this.tasks = new Task[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.tasks[i] = (Task) array[i];
        }
    }

    //TODO allowDuplicate
    @Override
    public Task generate() {
        Random random = new Random();
        int index = random.nextInt(tasks.length);
        return tasks[index];
    }

    private final Task[] tasks;
}
