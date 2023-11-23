package by.mnik0_0.quizer.task_generators;
import by.mnik0_0.quizer.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    private ArrayList<Task.Generator> generators = new ArrayList<>();

    public GroupTaskGenerator(Task.Generator... generators) {
        Collections.addAll(this.generators, generators);
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators.addAll(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        ArrayList<Task.Generator> availableGenerators = new ArrayList<>(generators);
        Random random = new Random();

        while (!availableGenerators.isEmpty()) {
            int index = random.nextInt(availableGenerators.size());
            Task.Generator generator = availableGenerators.get(index);

            try {
                return generator.generate();
            } catch (Exception e) {
                availableGenerators.remove(index);
            }
        }

        throw new IllegalStateException("can't generate");
    }
}