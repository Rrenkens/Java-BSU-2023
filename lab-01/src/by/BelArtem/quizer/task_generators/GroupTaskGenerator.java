package by.BelArtem.quizer.task_generators;

import by.BelArtem.quizer.Task;
import by.BelArtem.quizer.TaskGenerator;
import by.BelArtem.quizer.exceptions.GroupTaskGeneratorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class GroupTaskGenerator implements TaskGenerator {
    private final Collection<TaskGenerator> generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<>(Arrays.asList(generators));
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
        Task result;
        int randomGeneratorIndex;
        Random random = new Random();
        int size = generators.size();

        for (int i = 0; i < 10; ++i){
            randomGeneratorIndex = random.nextInt(size);
            for (TaskGenerator generator: generators){
                if (randomGeneratorIndex == 0){
                    try{
                        result = generator.generate();
                        return result;
                    } catch (Exception e){
                        break;
                    }
                }
                randomGeneratorIndex--;
            }
        }

        /**
         *  trying to use any generator
         */

        for (TaskGenerator generator: generators){
            try{
                return generator.generate();
            } catch (Exception e){
                continue;
            }
        }
        throw new GroupTaskGeneratorException("All generators have thrown exceptions");
    }
}
