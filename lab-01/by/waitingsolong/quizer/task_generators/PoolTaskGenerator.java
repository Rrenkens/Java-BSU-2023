package by.waitingsolong.quizer.task_generators;

import by.waitingsolong.quizer.Task;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator {
    public class RandomSet<T> {
        private final List<T> list = new ArrayList<>();
        private final Map<T, Integer> map = new HashMap<>();
        private final Random rand = new Random();

        public int size() {
            return list.size();
        }

        public boolean add(T val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        private boolean remove(T val) {
            if (!map.containsKey(val)) {
                return false;
            }
            T lastElement = list.get(list.size() - 1);
            int idx = map.get(val);

            list.set(idx, lastElement);
            map.put(lastElement, idx);

            list.remove(list.size() - 1);
            map.remove(val);
            return true;
        }

        private T getRandom() {
            return list.get(rand.nextInt(list.size()));
        }

        public T popRandom() {
            T randValue = this.getRandom();
            this.remove(randValue);
            return randValue;
        }
    }

    RandomSet<Task> tasks = new RandomSet<>();

    // for external check for how many tasks maximum might be provided in case of (allowDuplicate == false)
    public int getPoolSize() {
        return tasks.size();
    }

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
        for (Task task : tasks) {
            this.tasks.add(task);
        }
        this.allowDuplicate = allowDuplicate;
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
            this.tasks.add(task);
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (this.tasks.size() == 0) {
            throw new RuntimeException("No tasks provided to PoolTaskGenerator");
        }

        if (allowDuplicate) {
            return tasks.getRandom();
        } else {
            return tasks.popRandom();
        }
    }
}
