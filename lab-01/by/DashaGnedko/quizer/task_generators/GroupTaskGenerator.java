package by.DashaGnedko.quizer.task_generators;

import by.DashaGnedko.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

public class GroupTaskGenerator implements Task.Generator {
    private ArrayList<Task.Generator> generators;
    private Random random = new Random();

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<>(Arrays.stream(generators).toList());
    }

    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = (ArrayList<Task.Generator>) generators;
    }

    public Task generate() {
        int index = random.nextInt(0, generators.size());
        try {
            Task task = generators.get(index).generate();
            return task;
        } catch (Exception e) {
            generators.remove(index);
            generators.remove(index);
        }
        throw new IllegalArgumentException("All generators throw exceptions");
    }
}
