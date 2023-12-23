package by.AlexHanimar.quizer.task_generators;

import by.AlexHanimar.quizer.TaskGenerator;
import by.AlexHanimar.quizer.Task;
import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {

    private final ArrayList<TaskGenerator> generators;
    private final Random rand;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) throws IllegalArgumentException {
        if (generators == null || generators.length == 0 || Arrays.asList(generators).contains(null))
            throw new IllegalArgumentException();
        this.generators = new ArrayList<>();
        this.generators.addAll(Arrays.asList(generators));
        this.rand = new Random();
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) throws IllegalArgumentException {
        if (generators == null || generators.isEmpty() || generators.contains(null))
            throw new IllegalArgumentException();
        this.generators = new ArrayList<>();
        this.generators.addAll(generators);
        this.rand = new Random();
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() throws TaskGenerationException {
        var available = new ArrayList<>(generators);
        while (!available.isEmpty()) {
            int id = rand.nextInt(available.size());
            var gen = available.get(id);
            try {
                return gen.generate();
            } catch (TaskGenerationException ex) {
                available.remove(id);
            }
        }
        throw new TaskGenerationException();
    }
}
