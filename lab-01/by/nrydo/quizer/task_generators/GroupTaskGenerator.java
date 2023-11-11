package by.nrydo.quizer.task_generators;

import by.nrydo.quizer.Task;
import by.nrydo.quizer.TaskGenerator;
import by.nrydo.quizer.exceptions.TaskGenerationException;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {

    private final ArrayList<TaskGenerator> generators = new ArrayList<>();

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        Collections.addAll(this.generators, generators);
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators.addAll(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws TaskGenerationException {
        var random = new Random();
        while (!generators.isEmpty()) {
            int generator_index = random.nextInt(generators.size());
            try {
                return generators.get(generator_index).generate();
            } catch (Exception e) {
                generators.remove(generator_index);
            }
        }
        throw new TaskGenerationException();
    }
}
