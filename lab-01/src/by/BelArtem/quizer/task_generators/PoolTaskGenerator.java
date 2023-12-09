package by.BelArtem.quizer.task_generators;

import by.BelArtem.quizer.Task;
import by.BelArtem.quizer.TaskGenerator;
import by.BelArtem.quizer.exceptions.PoolTaskGeneratorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements TaskGenerator {
    boolean allowDuplicate;
    private final Collection<Task> tasks;

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
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection
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
    @Override
    public Task generate() throws Exception{
        if (tasks.isEmpty()) {
            throw new PoolTaskGeneratorException("\nError: PoolTaskGenerator can not generate more tasks(");
        }
        Task task = null;
        Random random = new Random();
        int randomTaskIndex = random.nextInt(tasks.size());
        for (Task curTask: tasks){
            if (randomTaskIndex == 0){
                task = curTask;
            }
            randomTaskIndex--;
        }
        if (!allowDuplicate && task != null) {
            tasks.remove(task);
        }
        return task;
    }
}
