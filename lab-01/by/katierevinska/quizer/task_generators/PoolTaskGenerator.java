package by.katierevinska.quizer.task_generators;


import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.exceptions.QuizFinishedException;

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
public class PoolTaskGenerator implements Task.Generator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    private boolean allowDuplicate;
    private LinkedList<Object> tasks = new LinkedList<>();
    public PoolTaskGenerator(
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
        if(tasks.size()==0){
            throw new QuizFinishedException("all tasks pool and duplicates aren't allowed");
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, tasks.size());
        if (!allowDuplicate) {
            return (Task) tasks.remove(randomNum);
        }else {
            return (Task) tasks.get(randomNum);
        }
    }

}