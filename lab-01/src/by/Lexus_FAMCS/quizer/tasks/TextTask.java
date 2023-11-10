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
    double result;
    private static final double eps = 1e-6;
    TextTask(
            String text,
            double result
    ) {
        this.text = text;
        this.result = result;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        double ans;
        try {
            ans = Double.parseDouble(answer);
        } catch (NumberFormatException exc) {
            return Result.INCORRECT_INPUT;
        }
        return Math.abs((double) Math.round(result * 1000) / 1000 - ans) < eps ? Result.OK : Result.WRONG ;
    }
}
