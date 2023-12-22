package by.lokdestro.quizer.task_generators;

import java.util.Collection;

import by.lokdestro.quizer.tasks.Task;

class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */

    int getSize(Task... tasks) {
        int ans = 0;
        for (int i = 0;i < tasks.length; ++i) {
            boolean was = false;
            for (int j = 0;j < i; ++j) {
                if (tasks[i].equals(tasks[j])) {
                    was = true;
                }
            }
            if (!was) {
                ans++;
            }
        }
        return ans;
    }

    int getSize(Collection<Task> tasks) {
        int ans = 0;
        for (int i = 0;i < tasks.size(); ++i) {
            boolean was = false;
            for (int j = 0;j < i; ++j) {
                if (tasks.toArray()[i].equals(tasks.toArray()[j])) {
                    was = true;
                }
            }
            if (!was) {
                ans++;
            }
        }
        return ans;
    }

    PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        if (allowDuplicate) {
            pool = new Task[getSize(tasks)];
            int ind = 0;
            for (int i = 0;i < tasks.length; ++i) {
                boolean was = false;
                for (int j = 0;j < i; ++j) {
                    if (tasks[i].equals(tasks[j])) {
                        was = true;
                    }
                }
                if (!was) {
                    pool[ind++] = tasks[i];
                }
            }
        } else {
            pool = new Task[tasks.length];
            for (int i = 0; i < tasks.length; ++i) {
                pool[i] = tasks[i];
            }
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
        if (allowDuplicate) {
            pool = new Task[getSize(tasks)];
            int ind = 0;
            for (int i = 0;i < tasks.size(); ++i) {
                boolean was = false;
                for (int j = 0;j < i; ++j) {
                    if (tasks.toArray()[i].equals(tasks.toArray()[j])) {
                        was = true;
                    }
                }
                if (!was) {
                    pool[ind++] = (Task) tasks.toArray()[i];
                }
            }
        } else {
            pool = new Task[tasks.size()];
            for (int i = 0; i < tasks.size(); ++i) {
                pool[i] = (Task) tasks.toArray()[i];
            }
        }
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        return pool[GenerateNumber(0,pool.length)];
    }

    @Override
    public int GenerateNumber(int max, int min) {
        return (int)(Math.random()*(max-min+1)+min);
    }

    Task[] pool;
}