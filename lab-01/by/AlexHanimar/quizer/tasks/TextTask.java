package by.AlexHanimar.quizer.tasks;

import by.AlexHanimar.quizer.Result;
import by.AlexHanimar.quizer.Task;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    private final String text;
    private final String answer;

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer.equals(this.answer))
            return Result.OK;
        else
            return Result.WRONG;
    }

}
