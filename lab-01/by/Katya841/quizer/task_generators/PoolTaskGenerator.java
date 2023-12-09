package by.Katya841.quizer.task_generators;


import by.Katya841.quizer.Rand;
import by.Katya841.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PoolTaskGenerator implements Task.Generator {
    private final boolean allowDuplicate;
    private ArrayList<Task> tasks;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.allowDuplicate  = allowDuplicate;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));

    }

    PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(tasks);
    }

    public Task generate() {
        if (!tasks.isEmpty()) {
            int pos = Rand.generateNumber(0, tasks.size() - 1);
            Task task;
            task = tasks.get(pos);
            if (!allowDuplicate) {
                tasks.remove(pos);
            }
            return task;
        } else {
            throw new RuntimeException("Tasks is empty in poolTaskGenerator");
        }
    }
}
