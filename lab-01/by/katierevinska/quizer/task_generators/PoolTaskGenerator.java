package by.katierevinska.quizer.task_generators;


import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.exceptions.QuizFinishedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PoolTaskGenerator implements Task.Generator {

    private final boolean allowDuplicate;
    private ArrayList<Object> tasks = new ArrayList<>();

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(List.of(tasks));
    }

    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
    }

    public Task generate() {
        if (tasks.size() == 0) {
            throw new QuizFinishedException("all tasks have been generated, can't generate more");
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, tasks.size());
        if (!allowDuplicate) {
            return (Task) tasks.remove(randomNum);
        } else {
            return (Task) tasks.get(randomNum);
        }
    }

}