package by.busskov.quizer.task_generators;

import by.busskov.quizer.Task;
import by.busskov.quizer.TaskGenerator;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = Arrays.copyOf(generators, generators.length);
    }

    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        Object[] array = generators.toArray();
        this.generators = new TaskGenerator[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.generators[i] = (TaskGenerator) array[i];
        }
    }

    @Override
    public Task generate() {
        Random random = new Random();
        ArrayList<TaskGenerator> generatorsCopy = new ArrayList<>(List.of(generators));
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

    private final TaskGenerator[] generators;
}
