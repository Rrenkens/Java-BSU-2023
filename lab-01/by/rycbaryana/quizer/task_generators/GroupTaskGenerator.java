package by.rycbaryana.quizer.task_generators;

import by.rycbaryana.quizer.tasks.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class GroupTaskGenerator implements Task.Generator {
    ArrayList<Task.Generator> generators;
    Random random = new Random();
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<>(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = (ArrayList<Task.Generator>) generators;
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        while (!generators.isEmpty()) {
            int index = random.nextInt(0, generators.size());
            try {
                return generators.get(index).generate();
            } catch (Exception e) {
                generators.remove(index);
            }
        }
        throw new IndexOutOfBoundsException("No generators left");
    }
}