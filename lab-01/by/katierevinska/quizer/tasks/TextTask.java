package by.katierevinska.quizer.tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.//TODO
 */
public class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    private final String text;
    private final String answer;

    TextTask(
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
        if(answer == this.answer){
            return Result.OK;
        }
        return Result.WRONG;
        //TODO do I need to invalid answer?
    }

    // ...
}
