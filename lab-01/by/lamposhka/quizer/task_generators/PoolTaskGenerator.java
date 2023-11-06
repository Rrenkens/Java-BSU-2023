package by.lamposhka.quizer.task_generators;

import by.lamposhka.quizer.tasks.Task;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator {
    private final boolean allowDuplicate;
    private final ArrayList<Task> tasks;
    private HashSet<Integer> generatedIndexes = new HashSet<>();
    private final Random random = new Random();

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
        this.tasks = new ArrayList<>(List.of(tasks));
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
        this.tasks = new ArrayList<Task>();
        this.tasks.addAll(tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (allowDuplicate) {
            return tasks.get(random.nextInt(tasks.size()));
        }
        int index = random.nextInt(tasks.size());
        while (generatedIndexes.contains(index)) {
            index = random.nextInt(tasks.size());
        }
        generatedIndexes.add(index);
        return tasks.get(index);
    }
}