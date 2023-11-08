package by.busskov.quizer.task_generators;

import by.busskov.quizer.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = Arrays.copyOf(generators, generators.length);
    }

    public GroupTaskGenerator(Collection<? extends Task.Generator> generators) {
        Object[] array = generators.toArray();
        this.generators = new Task.Generator[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.generators[i] = (Task.Generator) array[i];
        }
    }

    @Override
    public Task generate() {
        Random random = new Random();
        ArrayList<Task.Generator> generatorsCopy = new ArrayList<>(List.of(generators));
        while (!generatorsCopy.isEmpty()) {
            int index = random.nextInt(generatorsCopy.size());
            try {
                return generatorsCopy.get(index).generate();
            } catch (Throwable exception) {
                generatorsCopy.remove(index);
            }
        }
        throw new IllegalStateException("All generators threw exceptions");
    }

    private final Task.Generator[] generators;
}
