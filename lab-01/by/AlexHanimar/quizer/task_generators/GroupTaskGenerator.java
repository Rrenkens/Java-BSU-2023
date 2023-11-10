package by.AlexHanimar.quizer.task_generators;

import by.AlexHanimar.quizer.TaskGenerator;
import by.AlexHanimar.quizer.Task;
import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {

    private final ArrayList<TaskGenerator> generators;

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
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() throws TaskGenerationException {
        if (generators.isEmpty())
            throw new TaskGenerationException();
        var rand = new Random();
        var mask = new ArrayList<Boolean>(generators.size());
        for (int i = 0;i < generators.size();i++)
            mask.add(true);
        int free = generators.size();
        while (free > 0) {
            int id = rand.nextInt(generators.size());
            if (!mask.get(id))
                continue;
            try {
                return generators.get(id).generate();
            } catch (TaskGenerationException ex) {
                mask.set(id, false);
                --free;
            }
        }
        throw new TaskGenerationException();
    }
}
