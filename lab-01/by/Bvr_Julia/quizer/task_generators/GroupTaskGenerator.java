package by.Bvr_Julia.quizer.task_generators;

import by.Bvr_Julia.quizer.Randomizer;
import by.Bvr_Julia.quizer.tasks.Task;
import by.Bvr_Julia.quizer.exeptions.QuizAllGeneratorFellException;
import by.Bvr_Julia.quizer.exeptions.QuizNoGeneratorsException;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupTaskGenerator implements TaskGenerator {
    private final ArrayList<TaskGenerator> generators = new ArrayList<>();
    /**
     * @param generators generators that are passed to the constructor separated by commas
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators.addAll(Arrays.asList(generators));
        if (generators.length == 0){
            throw new QuizNoGeneratorsException();
        }
    }

    /**
     * A constructor that accepts a collection of generators
     *
     * @param generators generators that are passed to the constructor in the Collection (for example, {@link ArrayList})
     */
    GroupTaskGenerator(ArrayList<TaskGenerator> generators) {
        this.generators.addAll(generators);
        if (generators.isEmpty()){
            throw new QuizNoGeneratorsException();
        }
    }

    /**
     * @return the result of the generate() method of a random generator from a list.
     *         If this generator throws an exception in the generate() method, another one is selected.
     *         If all generators throw an exception, then an exception is thrown here too.
     */
    @Override
    public Task generate() {
        int randGenerator = Randomizer.generate(0,generators.size()-1);
        int lastNumber = randGenerator - 1;
        while (randGenerator != lastNumber) {
            try {
                 Task task = generators.get(randGenerator).generate();
                 return task;
            } catch (RuntimeException e) {
                if (randGenerator < generators.size()-1){
                    randGenerator++;
                }else{
                    randGenerator = 0;
                    lastNumber++;
                }
            }
        }
        throw new QuizAllGeneratorFellException();
    }
}