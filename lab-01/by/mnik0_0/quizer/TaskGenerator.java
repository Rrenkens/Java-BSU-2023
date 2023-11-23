package by.mnik0_0.quizer;

import by.mnik0_0.quizer.Task;

/**
 * Interface, который описывает один генератор заданий
 */
public interface TaskGenerator {
    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see Task
     */
    Task generate();
}
