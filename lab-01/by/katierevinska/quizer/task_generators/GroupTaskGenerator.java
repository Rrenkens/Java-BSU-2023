package by.katierevinska.quizer.task_generators;

import by.katierevinska.quizer.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GroupTaskGenerator implements Task.Generator {
    private ArrayList<Task.Generator> generators = new ArrayList<>();

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators.addAll(Arrays.asList(generators));
    }

    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators.addAll(generators);

    }

    public Task generate() {
        for (Task.Generator generator : generators) {
            try {
                return generator.generate();
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("can't generate any task");
    }
}