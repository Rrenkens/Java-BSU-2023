package by.nrydo.quizer.task_generators;

import by.nrydo.quizer.Task;
import by.nrydo.quizer.TaskGenerator;
import by.nrydo.quizer.exceptions.TaskGenerationException;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {

    private final boolean allowDuplicate;
    private final ArrayList<Task> tasks = new ArrayList<>();

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
        Collections.addAll(this.tasks, tasks);
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() throws TaskGenerationException {
        if (tasks.isEmpty()) {
            throw new TaskGenerationException();
        }
        var random = new Random();
        var task = tasks.get(random.nextInt(tasks.size()));
        if (!allowDuplicate) {
            tasks.remove(task);
        }
        return task;
    }
}