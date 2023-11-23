package by.mnik0_0.quizer.task_generators;

import by.mnik0_0.quizer.Task;

import java.util.*;

class PoolTaskGenerator implements Task.Generator {
    private ArrayList<Task> taskList = new ArrayList<>();
    private Random random = new Random();
    private boolean allowDuplicate;

    PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        Collections.addAll(this.taskList, tasks);
    }

    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        taskList.addAll(tasks);
    }

    public Task generate() {
        if (taskList.isEmpty()) {
            throw new IllegalStateException("Task list is empty");
        }

        int index = random.nextInt(taskList.size());
        Task task = taskList.get(index);

        if (!allowDuplicate) {
            taskList.remove(index);
        }

        return task;
    }
}