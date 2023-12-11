package by.lokdestro.quizer.task_generators;
import by.lokdestro.quizer.tasks.*;

public interface TaskGenerator {
    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see    Task
     */
    int GenerateNumber(int max, int min);
    Task generate();

}