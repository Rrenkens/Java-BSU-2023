package by.fact0rial.quizer.tasks;

import by.fact0rial.quizer.Result;
import by.fact0rial.quizer.Task;
import by.fact0rial.quizer.task_generators.PoolTaskGenerator;
/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    final private String txt;
    final private String ans;

    @Override
    public String getText() {
        return txt;
    }

    @Override
    public Result validate(String answer) {
        if (this.ans.equals(answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(
            String text,
            String answer
    ) {
        this.ans = answer;
        this.txt = text;
    }
}