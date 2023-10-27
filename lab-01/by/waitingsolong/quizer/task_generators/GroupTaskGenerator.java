package by.waitingsolong.quizer.task_generators;

import by.waitingsolong.quizer.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {
    Task.Generator[] generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new Task.Generator[generators.length];
        for (int i = 0; i < generators.length; i++) {
            this.generators[i] = generators[i];
        }
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = generators.toArray(new Task.Generator[generators.size()]);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        if (generators.length == 0) {
            throw new RuntimeException("There are no generators in GroupTaskGenerator");
        }

        boolean[] avaiableChoice = new boolean[generators.length];
        for (int i = 0; i < avaiableChoice.length; i++) {
            avaiableChoice[i] = true;
        }
        Random rand = new Random();
        int randIndex;
        int attempts = 0;
        Task task;
        while (true) {
            if (attempts == generators.length) {
                throw new RuntimeException("All generators throw exceptions");
            }

            while (true) {
                randIndex = rand.nextInt(generators.length);
                if (avaiableChoice[randIndex]) {
                    break;
                }
            }

            try {
                task = generators[randIndex].generate();
                break;
            } catch (RuntimeException e) {
                ++attempts;
                avaiableChoice[randIndex] = false;
            }
        }

        return task;
    }
}