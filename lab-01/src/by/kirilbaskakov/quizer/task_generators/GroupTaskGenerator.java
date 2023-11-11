package by.kirilbaskakov.quizer.task_generators;

import by.kirilbaskakov.quizer.Task;
import by.kirilbaskakov.quizer.exceptions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

public class GroupTaskGenerator implements Task.Generator {
	private final Collection<Task.Generator> generators;
	
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
    	this.generators = new ArrayList<>(Arrays.asList(generators));
    	if (this.generators.isEmpty()) {
    		throw new IllegalArgumentException("Error! Generators list is empty");
    	}
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
    	this.generators = generators;
    	if (this.generators.isEmpty()) {
    		throw new IllegalArgumentException("Error! Generators list is empty");
    	}
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
    	 ArrayList<Task.Generator> generatorList = new ArrayList<Task.Generator>(generators);
         while (!generatorList.isEmpty()) {
             int randomIndex = (new Random()).nextInt(generatorList.size());
             Task.Generator randomGenerator = generatorList.get(randomIndex);
             try {
                 Task task = randomGenerator.generate();
                 return task;
             } catch (Exception e) {
                 generatorList.remove(randomIndex);
             }
         }
         throw new AllGeneratorsFailedException("All generators failed to generate a task");
    }
}