package by.waitingsolong.quizer.tasks;

import by.waitingsolong.quizer.Result;
import by.waitingsolong.quizer.Task;
import by.waitingsolong.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    protected final String text;
    protected final String answer;

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
        return this.text;
    }

    public String getAnswer() {return this.answer;}

    @Override
    public Result validate(String answer) {
        if (this.answer.equals(answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}