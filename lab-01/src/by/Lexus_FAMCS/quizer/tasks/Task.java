package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    public interface Generator {
        Task generate();
    }
    /**
     @return текст задания
     */
    String getText();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);
}
