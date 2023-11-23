package by.mnik0_0.quizer.task_generators;

import by.mnik0_0.quizer.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {

    private ArrayList<Task.Generator> generators = new ArrayList<>();

    public GroupTaskGenerator(Task.Generator... generators) {
        Collections.addAll(this.generators, generators);
    }

    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators.addAll(generators);
    }

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