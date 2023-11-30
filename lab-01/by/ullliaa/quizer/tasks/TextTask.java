package by.ullliaa.quizer.by.ullliaa.quizer.tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Task;
import by.ullliaa.quizer.by.ullliaa.quizer.Result;
import by.ullliaa.quizer.by.ullliaa.quizer.task_generators.PoolTaskGenerator;

import java.util.Objects;

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
        if (answer.isEmpty()) {
            return Result.INCORRECT_INPUT;
        }
        if (Objects.equals(answer, this.answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
     }
}
