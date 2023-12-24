package by.arteman17.quizer.task_generators;

import by.arteman17.quizer.Task;
import by.arteman17.quizer.TaskGenerator;
import by.arteman17.quizer.exceptions.CantGenerateTask;

import java.util.*;
import java.util.stream.Collectors;


public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */

    private final boolean allowDup;
    private final ArrayList<Integer> used;
    private final List<Task> tasks;
    int count = 0;

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        allowDup = allowDuplicate;
        this.tasks = Arrays.stream(tasks).collect(Collectors.toList());
        used = new ArrayList<>(this.tasks.size());
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            LinkedList<Task> tasks
    ) {
        if (tasks == null) {
            throw new IllegalArgumentException("Tasks is null");
        }
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Tasks is empty");
        }
        if (tasks.contains(null)) {
            throw new IllegalArgumentException("One of task is null");
        }

        allowDup = allowDuplicate;
        this.tasks = new ArrayList<>(tasks);
        used = new ArrayList<>(this.tasks.size());
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        Random random = new Random();
        int pos = random.nextInt(tasks.size());
        if (allowDup) {
            return tasks.get(pos);
        } else {
            if (used.get(pos) == 0) {
                used.set(pos, 1);
                ++count;
                return tasks.get(pos);
            } else {
                if (count == tasks.size()) {
                    throw new CantGenerateTask("No more task in quiz");
                }
                var tmp = tasks.stream().filter(task -> used.get(tasks.indexOf(task)) == 0).findAny();
                if (tmp.isPresent()) {
                    ++count;
                    used.set(tasks.indexOf(tmp.get()), 1);
                    return tmp.get();
                } else {
                    throw new CantGenerateTask("No more task in quiz");
                }
            }
        }
    }
}