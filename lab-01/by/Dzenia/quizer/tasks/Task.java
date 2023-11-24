package by.Dzenia.quizer.tasks;
import by.Dzenia.quizer.Result;
public interface Task {
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

