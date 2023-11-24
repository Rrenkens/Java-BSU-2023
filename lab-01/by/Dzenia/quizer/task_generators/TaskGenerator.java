package by.Dzenia.quizer.task_generators;

import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;

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
     Task generate() throws CannotGenerateTaskException;
}
//
