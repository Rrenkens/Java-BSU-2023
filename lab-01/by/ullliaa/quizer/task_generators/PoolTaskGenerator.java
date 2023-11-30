package by.ullliaa.quizer.by.ullliaa.quizer.task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.TaskGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.Task;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.ALotOfTasks;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.CantGenerateTask;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.NoTasks;

import java.util.*;
import java.util.ArrayList;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final int[] usedTask;
    int count = 0;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator (
            boolean allowDuplicate,
            Task... tasks
    ) {
        if (tasks.length == 0) {
            throw new NoTasks();
        }
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(Arrays.asList(tasks));
        usedTask = new int[tasks.length];
        for (var elem : usedTask) {
            elem = 0;
        }
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    PoolTaskGenerator (
            boolean allowDuplicate,
            Collection<Task> tasks) {
        if (tasks.isEmpty()) {
            throw new NoTasks();
        }
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
        usedTask = new int[tasks.size()];
        for (var elem : usedTask) {
            elem = 0;
        }
    }

    public void setTaskCount(int count) {
        if (count > tasks.size() && !allowDuplicate) {
            throw new ALotOfTasks();
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() throws Exception {
        if (tasks.isEmpty()) {
            throw new CantGenerateTask();
        }
        Random random = new Random();

        int index = random.nextInt(tasks.size());

        if (allowDuplicate) {
            return tasks.get(index);
        } else {
            while (count < tasks.size()) {
                if (usedTask[index] == 0) {
                    usedTask[index] = 1;
                    ++count;
                } else {
                    index = random.nextInt(tasks.size());
                    continue;
                }
                return tasks.get(index);
            }
        }
        if (count == tasks.size()) {
            throw new CantGenerateTask();
        }
        return tasks.get(index);
    }
}