package by.rycbaryana.quizer.tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;

public interface Task {
    interface Generator {
        Task generate();
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
    Result validate(Answer answer);
}
