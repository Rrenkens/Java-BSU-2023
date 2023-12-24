package by.arteman17.quizer.task_generators;

import by.arteman17.quizer.Task;
import by.arteman17.quizer.TaskGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    private final List<TaskGenerator> generators;

    public GroupTaskGenerator(TaskGenerator... generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Generators is null");
        }
        if (Arrays.stream(generators).findAny().isEmpty()) {
            throw new IllegalArgumentException("Generators is empty");
        }
        for (var gener : generators) {
            if (gener == null) {
                throw new IllegalArgumentException("Generators consist null");
            }
        }
        this.generators = Arrays.stream(generators).collect(Collectors.toList());
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Generators is null");
        }
        if (generators.isEmpty()) {
            throw new IllegalArgumentException("Generators is empty");
        }
        if (generators.contains(null)) {
            throw new IllegalArgumentException("One of generator is null");
        }

        this.generators = new ArrayList<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        Collections.reverse(generators);
        for (var generator : generators){
            try {
                return generator.generate();
            } catch (Exception ignored) {
                generators.remove(generator);
            }
        }
        throw new IllegalArgumentException("Every generator generate exception");
    }
}
