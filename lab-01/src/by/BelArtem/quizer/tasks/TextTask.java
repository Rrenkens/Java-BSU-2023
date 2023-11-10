package by.BelArtem.quizer.tasks;

import by.BelArtem.quizer.Result;
import by.BelArtem.quizer.Task;

/**
 * Задание с заранее заготовленным текстом.
 *
 */
public class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    final private String text;
    final private String answer;

    public TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public Result validate(String answer){
        if (this.answer.equalsIgnoreCase(answer)){
            return Result.OK;
        }
        return Result.WRONG;
    }
}
