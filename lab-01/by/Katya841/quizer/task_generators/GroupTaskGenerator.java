package by.Katya841.quizer.task_generators;

import by.Katya841.quizer.Rand;
import by.Katya841.quizer.exceptions.TaskGeneratingException;
import by.Katya841.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class GroupTaskGenerator implements Task.Generator {
    private ArrayList<Task.Generator> listGenerator;

    public GroupTaskGenerator(Task.Generator... generators) {
        listGenerator = new ArrayList<>(Arrays.asList(generators));
        if (listGenerator.isEmpty()) {
            throw new TaskGeneratingException("TaskGeneratingException : " + "Empty set of generators");
        }
    }


    GroupTaskGenerator(Collection<Task.Generator> generators) {
        listGenerator = new ArrayList<>(generators);
        if (listGenerator.isEmpty()) {
            throw new TaskGeneratingException("TaskGeneratingException : " + "Empty collection of generators");
        }
    }
    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */

    public Task generate() {
        Task task;
        int pos = Rand.generateNumber(0, listGenerator.size() - 1);
        task = listGenerator.get(pos).generate();
        return task;
    }

}

