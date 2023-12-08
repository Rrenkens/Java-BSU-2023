package by.Kra567.quizer.tasks;
import by.Kra567.quizer.basics.*;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements  Task{
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    private String text;
    private String answer;
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
        if (answer == this.answer){
            return Result.OK;
        }
        return Result.WRONG;
    }
}
