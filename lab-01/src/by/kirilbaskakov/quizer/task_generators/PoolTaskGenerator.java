package by.kirilbaskakov.quizer.task_generators;

import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import by.kirilbaskakov.quizer.Task;


public class PoolTaskGenerator implements Task.Generator {
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
    	 if (allowDuplicate) {
             this.tasks = Arrays.asList(tasks);
         } else {
             this.tasks = new HashSet<>(Arrays.asList(tasks));
         }
    	 if (this.tasks.isEmpty()) {
    		 throw new IllegalArgumentException("Error! Task list is empty");
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
        if (allowDuplicate) {
        	this.tasks = tasks;
        } else {
        	this.tasks = new HashSet<>(tasks);
        }
        if (this.tasks.isEmpty()) {
   		 throw new IllegalArgumentException("Error! Task list is empty");
   	 }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
    	int index = new Random().nextInt(tasks.size()); 
    	int i = 0;
        for (Task task : tasks) {
            if (i == index) {
                return task;
            }
            i++;
        }
        return null;
    }
}