package by.Dzenia.quizer.task_generators;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;
import by.Dzenia.quizer.tasks.math_tasks.AbstractMathTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class PoolGenerator implements Task.Generator {

    private boolean allowDuplicate;
    private ArrayList<Task> tasks;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
        this.allowDuplicate = allowDuplicate;
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.tasks = new ArrayList<>(tasks);
        this.allowDuplicate = allowDuplicate;
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() throws CannotGenerateTaskException {
        if (tasks.isEmpty()) {
            throw new CannotGenerateTaskException("No any task in pool tasks");
        }
        int position = AbstractMathTask.Generator.generatePositiveInt() % tasks.size();
        Task task = tasks.get(position);
        if (!allowDuplicate) {
            tasks.remove(position);
        }
        return task;
    }
}
