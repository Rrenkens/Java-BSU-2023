package by.arteman17.quizer.task_generators;

import by.arteman17.quizer.Task;
import by.arteman17.quizer.TaskGenerator;
import by.arteman17.quizer.exceptions.CantGenerateTask;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */

    private final boolean allowDup_;
    private int[] used_;
    private final List<Task> tasks_;
    int count = 0;

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        allowDup_ = allowDuplicate;
        tasks_ = Arrays.stream(tasks).collect(Collectors.toList());
        used_ = new int[tasks_.size()];
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

        allowDup_ = allowDuplicate;
        tasks_ = new ArrayList<>(tasks);
        used_ = new int[tasks_.size()];
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        Random random = new Random();
        int pos = random.nextInt(tasks_.size());
        if (allowDup_) {
            return tasks_.get(pos);
        } else {
            if (used_[pos] == 0) {
                used_[pos] = 1;
                ++count;
                return tasks_.get(pos);
            } else {
                if (count == tasks_.size()) {
                    throw new CantGenerateTask("No more task in quiz");
                }
                while (used_[pos] != 0) {
                    pos = random.nextInt(tasks_.size());
                }
                ++count;
                used_[pos] = 1;
                return tasks_.get(pos);
            }
        }
    }
}