package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.Task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PoolTaskGenerator implements TaskGenerator {
    Set<Task> tasks = new HashSet<>();
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
        for (Task task : tasks) {
            if (allowDuplicate || !this.tasks.contains(task)) this.tasks.add(task);
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
        for (Task task : tasks) {
            if (allowDuplicate || !this.tasks.contains(task)) this.tasks.add(task);
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        int num = (int) (Math.random() * tasks.size());
        Task res = null;
        int i = -1;
        for (Task task : tasks) {
            if (++i == num) {
                res = task;
                break;
            }
        }
        return res;
    }
}