package by.ullliaa.quizer.by.ullliaa.quizer.task_generators;
import by.ullliaa.quizer.by.ullliaa.quizer.TaskGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.Task;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.AllGeneratorsHaveException;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.NoGenerators;

import java.io.IOException;
import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {
    private final ArrayList<TaskGenerator> generators_ = new ArrayList<>();

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        if (generators.length == 0) {
            throw new NoGenerators();
        }
        generators_.addAll(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        if (generators.isEmpty()) {
            throw new NoGenerators();
        }
        generators_.addAll(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws Exception {
        int[] exceptInGenerators = new int[generators_.size()];
        for (int i = 0; i < generators_.size(); ++i) {
            exceptInGenerators[i] = 0;
        }

        int count = 0;

        Random random = new Random();
        int index = random.nextInt(generators_.size());

        while (count < generators_.size()) {
            try {
                return generators_.get(index).generate();
            } catch (IOException next) {
                if (exceptInGenerators[index] == 0) {
                    exceptInGenerators[index] = 1;
                    ++count;
                }
                index = random.nextInt(generators_.size());
            }
        }

        if (count == generators_.size()) {
            throw new AllGeneratorsHaveException();
        }
        return generators_.get(index).generate();
    }
}