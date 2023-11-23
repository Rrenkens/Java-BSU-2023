package by.mnik0_0.quizer.tasks;

import by.mnik0_0.quizer.Result;
import by.mnik0_0.quizer.Task;

import java.util.Objects;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {

    private String text;
    private String answer;
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */

    TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(String answer) {
        if(Objects.equals(answer, this.answer)){
            return Result.OK;
        }

        return Result.WRONG;
    }
}