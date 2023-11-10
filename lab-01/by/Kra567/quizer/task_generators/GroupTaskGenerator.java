package by.Kra567.quizer.task_generators;

import by.Kra567.quizer.basics.Task;
import by.Kra567.quizer.basics.TaskGenerator;

import java.util.*;
// UPDATE
public class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    private ArrayList<TaskGenerator> generators;
    private Random gen = new Random();
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<>(Arrays.stream(generators).toList());
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() throws Exception {

        int idx = gen.nextInt(generators.size());
        try{
            return generators.get(idx).generate();
        }catch (Exception e){
            if (generators.size() == 1){
                throw e;
            }
            else{
                generators.remove(idx);
                return generate();
            }
        }
    }
}
