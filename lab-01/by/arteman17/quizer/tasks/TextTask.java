package by.arteman17.quizer.tasks;

import by.arteman17.quizer.Result;
import by.arteman17.quizer.Task;
import by.arteman17.quizer.task_generators.PoolTaskGenerator;

import java.util.Objects;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */

public class TextTask implements Task {
    private final String text_;
    private final String correctAns_;

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(String text, String answer) {
        if (text == null || answer == null) {
            throw new IllegalArgumentException("At least one of the arguments is null");
        }

        if (text.isEmpty() || answer.isEmpty()) {
            throw new IllegalArgumentException("At least one of the arguments is empty");
        }

        text_ = text;
        correctAns_ = answer;
    }

    @Override
    public String getText() {
        return text_;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (answer.isEmpty()) {
            return Result.INCORRECT_INPUT;
        } else if (Objects.equals(answer, correctAns_)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
