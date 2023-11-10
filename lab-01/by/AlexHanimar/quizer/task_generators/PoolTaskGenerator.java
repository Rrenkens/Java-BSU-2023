package by.AlexHanimar.quizer.task_generators;

import by.AlexHanimar.quizer.TaskGenerator;
import by.AlexHanimar.quizer.Task;
import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private final ArrayList<Task> tasks;
    private ArrayList<Boolean> mask;
    private int cnt;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) throws IllegalArgumentException {
        if (tasks == null || tasks.length == 0 || Arrays.asList(tasks).contains(null))
            throw new IllegalArgumentException();
        this.tasks = new ArrayList<>();
        this.tasks.addAll(Arrays.asList(tasks));
        this.allowDuplicate = allowDuplicate;
        this.mask = new ArrayList<>();
        for (int i = 0;i < tasks.length;i++)
            this.mask.add(true);
        cnt = tasks.length;
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
    ) throws IllegalArgumentException {
        if (tasks == null || tasks.isEmpty() || tasks.contains(null))
            throw new IllegalArgumentException();
        this.tasks = new ArrayList<>();
        this.tasks.addAll(tasks);
        this.allowDuplicate = allowDuplicate;
        this.mask = new ArrayList<>();
        for (int i = 0;i < tasks.size();i++)
            this.mask.add(true);
        cnt = tasks.size();
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() throws TaskGenerationException {
        if (cnt <= 0)
            throw new TaskGenerationException();
        var rand = new Random();
        while (true) {
            int id = rand.nextInt(tasks.size());
            if (!mask.get(id))
                continue;
            if (!allowDuplicate) {
                --cnt;
                mask.set(id, false);
            }
            return tasks.get(id);
        }
    }
}
