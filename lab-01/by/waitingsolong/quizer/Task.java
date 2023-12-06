package by.waitingsolong.quizer;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /**
     * Interface, который описывает один генератор заданий
     */
    interface Generator {
        /**
         * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
         *
         * @return задание
         * @see    Task
         */
        Task generate();
    }

    /**
     @return текст задания
     */
    String getText();

    String getAnswer();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);
}