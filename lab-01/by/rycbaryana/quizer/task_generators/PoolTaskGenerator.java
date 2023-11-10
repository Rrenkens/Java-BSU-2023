package by.rycbaryana.quizer.task_generators;

import by.rycbaryana.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    ArrayList<Task> tasks;
    boolean allowDuplicate;
    Random random = new Random();
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = (ArrayList<Task>) tasks;
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new IndexOutOfBoundsException("No tasks left");
        }
        Task task = tasks.get(random.nextInt(0, tasks.size()));
        if (!allowDuplicate) {
            tasks.remove(task);
        }
        return task;
    }
}