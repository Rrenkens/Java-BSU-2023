package by.AlexHanimar.quizer;

import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

/**
 * Interface, который описывает одно задание
 */
public interface Task {

    interface Generator extends TaskGenerator {
        public Task generate() throws TaskGenerationException;
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
