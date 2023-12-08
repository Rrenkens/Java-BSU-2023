package by.fact0rial.quizer.task_generators;

import by.fact0rial.quizer.Task;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator{
    Random rand = new Random();
    final private ArrayList<Task> tasks;
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
        if (allowDuplicate) {
            this.tasks = new ArrayList<>(Arrays.asList(tasks));
        } else {
            var set = new HashSet<Task>(Arrays.asList(tasks));
            this.tasks = new ArrayList<>(set);
        }
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
        if (allowDuplicate) {
            this.tasks = new ArrayList<>(tasks);
        } else  {
            var set = new HashSet<>(tasks);
            this.tasks = new ArrayList<>(set);
        }
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Trying to pass empty collection to PoolTask.Generator constructor");
        }
    }
    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        return this.tasks.get(rand.nextInt(this.tasks.size()));
    }
}
