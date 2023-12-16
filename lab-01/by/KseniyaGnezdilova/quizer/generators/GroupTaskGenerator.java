package by.KseniyaGnezdilova.quizer.generators;

import by.KseniyaGnezdilova.quizer.tasks.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class GroupTaskGenerator implements Task.Generator {
    Vector<Task.Generator> generators = new Vector<>();
    public GroupTaskGenerator(Task.Generator... generators) {
        Collections.addAll(this.generators, generators);
    }

    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators.addAll(generators);
    }

    public Task generate() {
        Random random = new Random();
        while (!generators.isEmpty()){
            int pos = random.nextInt(this.generators.size());
            try {
                return generators.get(pos).generate();
            } catch (Exception e) {
                generators.remove(pos);
            }
        }
        throw new IndexOutOfBoundsException("No generators");
    }

}
