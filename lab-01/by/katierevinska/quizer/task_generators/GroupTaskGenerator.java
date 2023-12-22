package by.katierevinska.quizer.task_generators;

import by.katierevinska.quizer.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.pow;

public class GroupTaskGenerator implements Task.Generator {
    private ArrayList<Task.Generator> generators = new ArrayList<>();

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators.addAll(Arrays.asList(generators));
    }

    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators.addAll(generators);

    }

    public Task generate() {
        while(generators.size() != 0){
            int index = ThreadLocalRandom.current().nextInt(0, generators.size());
            try {
                return generators.get(index).generate();
            } catch (Exception exp) {
                generators.remove(index);
            }
        }
        throw new IllegalArgumentException("can't generate any task");
    }
}