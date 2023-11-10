package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;
import by.Lexus_FAMCS.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    String text;
    String answer;
    private static final double eps = 1e-6;
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
        return answer.equals(this.answer) ? Result.OK : Result.WRONG;
    }
}
