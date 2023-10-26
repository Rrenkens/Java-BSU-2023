package by.MikhailShurov.quizer.task_generators;

import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.TaskGenerator;

import java.util.Collection;
import java.util.ArrayList;

public class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    GroupTaskGenerator(TaskGenerator... generators) {
        // ...
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        // ...
    }

    @Override
    public Task generate() {
        return null;
    }

//    /**
//     * @return результат метода generate() случайного генератора из списка.
//     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
//     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
//     */
//    Task generate() {
//        // ...
//    }
}
