package by.Bvr_Julia.quizer.task_generators;

import by.Bvr_Julia.quizer.Randomizer;
import by.Bvr_Julia.quizer.tasks.Task;
import by.Bvr_Julia.quizer.exeptions.QuizAllGeneratorFellException;

import java.util.ArrayList;
import java.util.List;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Integer> usedTasks;

    /**
     * @param allowDuplicate allow duplicates
     * @param tasks          that are passed to the constructor separated by commas
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(List.of(tasks));
        if(!allowDuplicate){
            usedTasks = new ArrayList<>();
            for (int i = 0; i< tasks.length;i++){
                usedTasks.add(1);
            }
        }
    }

    /**
     * A constructor that accepts a collection of tasks
     *
     * @param allowDuplicate allow duplicates
     * @param tasks          tasks that are passed to the constructor in the Collection
     */

    public PoolTaskGenerator(
            boolean allowDuplicate,
            ArrayList<Task> tasks
    ) {
        this.allowDuplicate = allowDuplicate;
        this.tasks.addAll(tasks);
        if(!allowDuplicate){
            usedTasks = new ArrayList<>();
            for (int i = 0; i< tasks.size();i++){
                usedTasks.add(1);
            }
        }
    }

    /**
     * @return random task from the list
     */
    @Override
    public Task generate() {
        if (allowDuplicate) {
            int num = Randomizer.generate(0, tasks.size() - 1);
            return tasks.get(num);
        }else{
            int tmp = 0;
            for (Integer usedTask : usedTasks) {
                if (usedTask == 1) {
                    tmp++;
                }
            }
            int num = Randomizer.generate(0, tmp - 1);
            tmp = 0;
            for (int i = 0; i<tasks.size();i++) {
                if (usedTasks.get(i) == 1) {
                    if (num == tmp) {
                        return tasks.get(i);
                    } else {
                        tmp++;
                    }
                }
            }

        }
        throw new QuizAllGeneratorFellException();
    }
}