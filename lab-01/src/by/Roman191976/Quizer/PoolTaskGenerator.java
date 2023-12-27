package by.Roman191976.Quizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {
    
    private List<Task> tasks;
    private Random random;
    private boolean allowDuplicate;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    PoolTaskGenerator(
        boolean allowDuplicate,
        Task... tasks
    ) {
        this.tasks = Arrays.asList(tasks);
        this.random = new Random();
        this.allowDuplicate = allowDuplicate;
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
        this.tasks = new ArrayList<>(tasks);
        this.random = new Random();
        this.allowDuplicate = allowDuplicate;
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new IllegalStateException("No tasks available");
        }

        int index = random.nextInt(tasks.size());
        Task task = tasks.get(index);

        if (!allowDuplicate) {
            tasks.remove(index);
        }

        return task;
    }
}