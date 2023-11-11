package by.N3vajnoKto.quizer.task_generators;

import by.N3vajnoKto.quizer.Task;
import by.N3vajnoKto.quizer.exception.NoTasksLeftException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class PoolTaskGenerator implements Task.Generator {
    private boolean dup = false;
    private int cur;
    private ArrayList<Task> tasks;
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.tasks = new ArrayList<Task>();
        this.dup = allowDuplicate;
        cur = 0;

        for (int i = 0; i < tasks.length; ++i) {
            this.tasks.addLast(tasks[i]);
        }

        Collections.shuffle(this.tasks);
    }
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.tasks = new ArrayList<Task>();
        this.dup = allowDuplicate;
        cur = 0;

        var it = tasks.iterator();
        while (it.hasNext()) {
            this.tasks.addLast(it.next());
        }
        Collections.shuffle(this.tasks);
    }

    public Task generate() throws NoTasksLeftException {
        if (this.dup) {
            var rnd = new Random();
            return this.tasks.get(Math.abs(rnd.nextInt()) % this.tasks.size());
        } else {
            if (cur == this.tasks.size()) {
                throw new NoTasksLeftException();
            }
            return this.tasks.get(cur++);
        }
    }
}