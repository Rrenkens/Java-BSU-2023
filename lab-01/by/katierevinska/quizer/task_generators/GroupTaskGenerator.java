package by.katierevinska.quizer.task_generators;

import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.TaskGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//```
//
//        ### GroupTaskGenerator
//        `TaskGenerator`, который позволяет объединить несколько других `TaskGenerator`.
//
//        ```java
class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    ArrayList<TaskGenerator> generators = new ArrayList<>();

    GroupTaskGenerator(TaskGenerator... generators) {
        this.generators.addAll(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators.addAll(generators);

    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        boolean flag = false;
        int i = 0;

        while(!(i==generators.size())){
            try{
                return generators.get(i).generate();
            }
            catch (Exception e){
                i++;
            }

        }
        throw new IllegalArgumentException("can't generate any task");
    }
}