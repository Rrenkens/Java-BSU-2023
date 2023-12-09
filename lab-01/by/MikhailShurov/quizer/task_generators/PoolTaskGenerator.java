package by.MikhailShurov.quizer.task_generators;

import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.TaskGenerator;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {

    private Set<Task> tasksSet;
    boolean allowDuplicate;

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
        this.tasksSet = new HashSet<>();
        if (allowDuplicate) {
            tasksSet.addAll(Arrays.asList(tasks));
        } else {
            for (Task task : tasks) {
                if (!tasksSet.contains(task)) {
                    tasksSet.add(task);
                }
            }
        }
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
        this.tasksSet = new HashSet<>();
        if (allowDuplicate) {
            tasksSet.addAll(tasks);
        } else {
            for (Task task : tasks) {
                if (!tasksSet.contains(task)) {
                    tasksSet.add(task);
                }
            }
        }
        System.out.println(tasksSet.size());
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        List<Task> tasksList = new ArrayList<>(tasksSet);
        Random random = new Random();
        if (tasksList.isEmpty()) {
            throw new NoSuchElementException("No more available tasks in the generator.");
        }
        int randomIndex = random.nextInt(tasksList.size());

        Task selectedTask = tasksList.get(randomIndex);
        if (!allowDuplicate) {
            tasksSet.remove(selectedTask);
        }

        return selectedTask;
    }
}
