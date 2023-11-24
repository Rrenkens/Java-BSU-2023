package by.Dzenia.quizer.task_generators;

import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {
    private ArrayList<TaskGenerator> generators;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<>(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws CannotGenerateTaskException {
        Collections.shuffle(this.generators);
        for (var generator: generators) {
            try {
                return generator.generate();
            } catch (Exception ignored) {}
        }
        throw new CannotGenerateTaskException("Cannot generate task in any generator");
    }
}
