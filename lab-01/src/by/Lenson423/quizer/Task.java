package by.Lenson423.quizer;

import by.Lenson423.quizer.exceptions.CantGenerateTask;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /**
     * @return текст задания
     */
    String getText();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param answer ответ на задание
     * @return результат ответа
     * @see Result
     */
    Result validate(String answer);

    interface Generator {
        /**
         * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
         *
         * @return задание
         * @see Task
         */
        Task generate() throws CantGenerateTask;
    }
}