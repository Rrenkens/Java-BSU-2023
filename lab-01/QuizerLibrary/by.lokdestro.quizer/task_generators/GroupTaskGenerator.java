package task_generators;

import java.util.Collection;

import tasks.Task;

class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    GroupTaskGenerator(TaskGenerator... generators) {
        // ...
    	gen = new TaskGenerator[generators.length];
    	int i = 0;
    	for (TaskGenerator arg : generators) {
    		gen[i++] = arg;
    	}
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
    	gen = new TaskGenerator[generators.size()];
    	int i = 0;
    	for (TaskGenerator arg : generators) {
    		gen[i++] = arg;
    	}
    }
    @Override
    public int GenerateNumber(int max, int min) {
        return (int)(Math.random()*(max-min+1)+min);  
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
    	int usedgen = GenerateNumber(0, gen.length);
    	return gen[usedgen].generate();
    }
    
    TaskGenerator[] gen;
}
