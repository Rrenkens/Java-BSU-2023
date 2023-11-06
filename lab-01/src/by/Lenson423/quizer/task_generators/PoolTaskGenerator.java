package by.Lenson423.quizer.task_generators;

import by.Lenson423.quizer.Task;
import by.Lenson423.quizer.exceptions.CantGenerateTask;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements Task.Generator {
    private final boolean allowDuplicate;
    private final List<Task> tasks;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = Arrays.stream(tasks).collect(Collectors.toList());
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
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() throws CantGenerateTask {
        if (tasks.isEmpty()) {
            throw new CantGenerateTask("Empty array with tasks");
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(0, tasks.size());
        Task res = tasks.get(randomIndex);
        if (!allowDuplicate) {
            tasks.remove(randomIndex);
        }
        return res;
    }
}
