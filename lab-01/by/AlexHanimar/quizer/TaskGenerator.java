package by.AlexHanimar.quizer;

import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

/**
 * Interface, который описывает один генератор заданий
 */
public interface TaskGenerator {
    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see    Task
     */
    Task generate() throws TaskGenerationException;
}
