package by.mnik0_0.quizer;

import by.mnik0_0.quizer.Result;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /**
     @return текст задания
     */
    String getText();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return результат ответа
     * @see           Result
     */
    Result validate(String answer);
}