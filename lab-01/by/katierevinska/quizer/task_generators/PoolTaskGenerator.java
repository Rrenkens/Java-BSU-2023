package by.katierevinska.quizer.task_generators;


import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.TaskGenerator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//```
//
//        ### PoolTaskGenerator
//        `TaskGenerator`, который отдает задания из заранее заготовленного набора.
//
//        ```java
public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    private boolean allowDuplicate;
    private LinkedList<Object> tasks = new LinkedList<>();
    PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(List.of(tasks));
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
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {


// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(0, tasks.size() + 1);//TODO can be faster
        if (!allowDuplicate) {
            return (Task) tasks.remove(randomNum);
        }
        return (Task) tasks.get(randomNum);
    }
    //TODO if it also was the last element
}