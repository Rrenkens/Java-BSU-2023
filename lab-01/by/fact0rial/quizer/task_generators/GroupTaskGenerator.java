package by.fact0rial.quizer.task_generators;

import by.fact0rial.quizer.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator{
    Random rand = new Random();
    final private ArrayList<Task.Generator> lists;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.lists = new ArrayList<>(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.lists = new ArrayList<>(generators);
        if(generators.isEmpty()) {
            throw new IllegalArgumentException("Trying to pass empty collection to GroupTask.Generator constructor");
        }
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        int num = rand.nextInt(this.lists.size());
        Task.Generator thing = this.lists.get(num);
        return thing.generate();
    }
}
