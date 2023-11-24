package by.Dzenia.quizer.tasks;
import by.Dzenia.quizer.Result;
import by.Dzenia.quizer.task_generators.PoolTaskGenerator;

import java.util.Objects;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {

    private String taskText;
    private String answer;
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(
            String text,
            String answer
    ) {
        this.taskText = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return taskText;
    }

    @Override
    public Result validate(String answer) {
        if (this.answer.equals(answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }

    // ...
}
