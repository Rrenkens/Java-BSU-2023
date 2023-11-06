package by.Lenson423.quizer.task_generators;

import by.Lenson423.quizer.Task;

import java.util.*;
import java.util.stream.Collectors;

public class GroupTaskGenerator implements Task.Generator {
    protected List<Task.Generator> taskGenerators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        taskGenerators = Arrays.stream(generators).collect(Collectors.toList());
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        taskGenerators = new ArrayList<>(generators);

    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        Collections.shuffle(taskGenerators);
        for (var generator : taskGenerators) {
            try {
                return generator.generate();
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("All generators generates exceptions");
    }
}