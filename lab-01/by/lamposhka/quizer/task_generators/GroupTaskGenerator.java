package by.lamposhka.quizer.task_generators;

import by.lamposhka.quizer.tasks.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {
    private final ArrayList<Task.Generator> generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<Task.Generator>(List.of(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        if (generators.isEmpty()) {
            throw new IllegalArgumentException("Empty generator collection for group task generator.");
        }
        this.generators = new ArrayList<Task.Generator>();
        this.generators.addAll(generators);
    }

    /**
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws Exception { // Make generate() throw exceptions.
        Random random = new Random();
        boolean[] isThrowingExceptions = new boolean[generators.size()];
        Arrays.fill(isThrowingExceptions, true);
        boolean noGeneratorsLeft;
        int index = 0;
        while (true) {
            try {
                index = random.nextInt(generators.size());
                return generators.get(index).generate();
            } catch (Exception e) {
                isThrowingExceptions[index] = true;
                noGeneratorsLeft = true;
                for (var i : isThrowingExceptions) {
                    if (!i) {
                        noGeneratorsLeft = false;
                        break;
                    }
                }
                if (noGeneratorsLeft) {
                    throw new Exception("All generators are throwing exceptions.");
                }
            }
        }
    }
}
