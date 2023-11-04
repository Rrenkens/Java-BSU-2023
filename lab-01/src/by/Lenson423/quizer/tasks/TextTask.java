package by.Lenson423.quizer.tasks;

import by.Lenson423.quizer.Result;
import by.Lenson423.quizer.Task;
import by.Lenson423.quizer.task_generators.PoolTaskGenerator;

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

        return answer.isEmpty() ?
                Result.INCORRECT_INPUT :
                (answer.equals(this.answer)) ? Result.OK : Result.WRONG;
    }
}
