package by.Kra567.quizer.task_generators;

import by.Kra567.quizer.basics.Task;
import by.Kra567.quizer.basics.TaskGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    private ArrayList<Task> tasks;
    private boolean allowDuplicate;
    private Random gen = new Random();
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(Arrays.stream(tasks).toList());
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
    public Task generate() throws Exception {
        int idx = gen.nextInt(tasks.size());
        if (!allowDuplicate && tasks.isEmpty()){
            throw new RuntimeException("PoolTaskGenerator : no tasks!!!");
        }
        Task res = tasks.get(idx);
        if (!allowDuplicate) {
            tasks.remove(idx);
        }
        return res;
    }
}