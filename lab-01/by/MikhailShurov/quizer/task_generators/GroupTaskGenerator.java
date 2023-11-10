package by.MikhailShurov.quizer.task_generators;

import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.TaskGenerator;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class GroupTaskGenerator implements TaskGenerator {
    private ArrayList<Task.Generator> generators;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<>();
        for (Task.Generator generator : generators) {
            this.generators.add(generator);
        }
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = new ArrayList<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
        Random random = new Random();
        int maxAttempts = generators.size();
        Set<Integer> excludedIndexes = new HashSet<>();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(generators.size());
            } while (excludedIndexes.contains(randomIndex));
            try {
                return generators.get(randomIndex).generate();
            } catch (Exception e) {
                excludedIndexes.add(randomIndex);
            }
        }

        throw new IllegalStateException("Unable to generate task. All generators threw exceptions.");
    }
}
