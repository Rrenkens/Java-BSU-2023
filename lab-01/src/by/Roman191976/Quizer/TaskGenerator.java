package by.Roman191976.Quizer;

/**
 * Interface, который описывает один генератор заданий
 */
interface TaskGenerator {
    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see    Task
     */
    Task generate();
}