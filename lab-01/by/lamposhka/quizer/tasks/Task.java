package by.lamposhka.quizer.tasks;

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
         * @see Task
         */
        Task generate() throws Exception;
    }

    /**
     * Enum, который описывает результат ответа на задание
     */
    enum Result {
        OK, // Получен правильный ответ
        WRONG, // Получен неправильный ответ
        INCORRECT_INPUT // Некорректный ввод. Например, текст, когда ожидалось число
    }

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
}