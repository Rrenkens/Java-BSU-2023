package by.rycbaryana.quizer.tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;
import by.rycbaryana.quizer.task_generators.TaskGenerator;

public interface Task {
    interface Generator extends TaskGenerator {
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
