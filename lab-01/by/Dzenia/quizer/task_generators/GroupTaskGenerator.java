package by.Dzenia.quizer.task_generators;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
public class GroupTaskGenerator implements TaskGenerator {
    private ArrayList<TaskGenerator> generators;
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
    public Task generate() throws CannotGenerateTaskException {
        Random random = new Random();
        while (!generators.isEmpty()){
            int pos = random.nextInt(0, generators.size());
            try {
                return generators.get(pos).generate();
            } catch (Exception ex) {
                generators.remove(pos);
            }
        }
        throw new CannotGenerateTaskException("Cannot generate task in any generator");
    }
}
