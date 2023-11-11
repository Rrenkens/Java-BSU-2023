package by.KseniyaGnezdilova.quizer.generators;

import by.KseniyaGnezdilova.quizer.tasks.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class PoolTaskGenerator implements Task.Generator {

    private Vector <Task> tasks = new Vector<>();
    private boolean allowDuplicate;

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        Collections.addAll(this.tasks, tasks);
    }

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
    }

    public Task generate() {
        if (this.tasks.isEmpty()){
            throw new IndexOutOfBoundsException("No more tasks");
        }
        Random random = new Random();
        int rnd = random.nextInt(tasks.size());
        Task task = tasks.get(rnd);
        if (!allowDuplicate) this.tasks.remove(rnd);
        return task;
    }
}