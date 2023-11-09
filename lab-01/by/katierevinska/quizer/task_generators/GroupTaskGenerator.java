package by.katierevinska.quizer.task_generators;

import by.katierevinska.quizer.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//```
//
//        ### GroupTaskGenerator
//        `TaskGenerator`, который позволяет объединить несколько других `TaskGenerator`.
//
//        ```java
public class GroupTaskGenerator implements Task.Generator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    private ArrayList<Task.Generator> generators = new ArrayList<>();

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators.addAll(Arrays.asList(generators));
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
    public Task generate(){
        for (Task.Generator generator : generators) {
            try {
                return generator.generate();
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("can't generate any task");
    }
}