package by.N3vajnoKto.quizer.task_generators;

import by.N3vajnoKto.quizer.Task;
import by.N3vajnoKto.quizer.exception.NoValidGeneratorsException;

import java.util.*;
import java.lang.Integer;

public class GroupTaskGenerator implements Task.Generator {
    private ArrayList<Task.Generator> generators;

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<Task.Generator>();

        for (int i = 0; i < generators.length; ++i) {
            this.generators.addLast(generators[i]);
        }

    }

    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = new ArrayList<Task.Generator>();

        var it = generators.iterator();
        while (it.hasNext()) {
            this.generators.addLast(it.next());
        }
    }


    @Override
    public Task generate() throws NoValidGeneratorsException {
        var arr = new ArrayList<Integer>();

        for (int i = 0; i < this.generators.size(); ++i) {
            arr.add(i);
        }

        Collections.shuffle(arr);

        for (var i : arr) {
            try {
                return generators.get(i).generate();
            } catch (Exception e) {
                continue;
            }
        }

        throw new NoValidGeneratorsException();
    }
}