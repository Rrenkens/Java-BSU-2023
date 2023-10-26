package by.MikhailShurov.quizer.tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.task_generators.PoolTaskGenerator;


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
    public TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text + " // x = " + this.answer;
    }

    @Override
    public Result validate(String answer) {
        return null;
    }

}
