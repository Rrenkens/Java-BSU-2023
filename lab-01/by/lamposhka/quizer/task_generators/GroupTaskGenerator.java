package by.lamposhka.quizer.task_generators;

import by.lamposhka.quizer.Task;
import by.lamposhka.quizer.TaskGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GroupTaskGenerator implements TaskGenerator {
    private final ArrayList<TaskGenerator> generators;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<TaskGenerator>(List.of(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<TaskGenerator>();
        this.generators.addAll(generators);
    }

    /**
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() { // Make generate() throw exceptions.
        Random random = new Random();
        boolean[] isThrowingExceptions = new boolean[generators.size()];
        boolean noGeneratorsLeft;
        int index = 0;
        while(true) {
            try {
                index = random.nextInt(generators.size());
                return generators.get(index).generate();
            } catch (Exception e) {
                isThrowingExceptions[index] = true;
                noGeneratorsLeft = true;
                for (var i :isThrowingExceptions) {
                    if (!i) {
                        noGeneratorsLeft = false;
                        break;
                    }
                }
                if (noGeneratorsLeft) {
                    System.out.println("Unexpected error occurred.");
                }
            }
        }
    }
}
