package by.busskov.quizer.task_generators;

import by.busskov.quizer.Task;
import by.busskov.quizer.TaskGenerator;

import java.util.Arrays;
import java.util.Collection;

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
        for (TaskGenerator generator : generators) {
            try {
                return generator.generate();
            } catch (Throwable exception) {
                // try next generator
            }
        }
        throw new IllegalStateException("All generators threw exceptions");
    }

    TaskGenerator[] generators;
}
