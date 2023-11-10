package by.Roman191976.Quizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class GroupTaskGenerator implements TaskGenerator {
    private List<TaskGenerator> generators;
    private Random random;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    GroupTaskGenerator(TaskGenerator... generators) {
        this(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<>(generators);
        this.random = new Random();
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
       if (generators.isEmpty()) {
            throw new IllegalStateException("No generators available");
        }

        while (!generators.isEmpty()) {
            int index = random.nextInt(generators.size());
            TaskGenerator generator = generators.get(index);
            try { 
                return generator.generate();
            } catch (Exception e) {
                generators.remove(generator);
            }
        }

        throw new IllegalStateException("All generators failed to generate a task");
    }
}

