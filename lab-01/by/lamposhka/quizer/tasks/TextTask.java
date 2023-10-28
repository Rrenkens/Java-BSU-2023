package by.lamposhka.quizer.tasks;

import by.lamposhka.quizer.Result;
import by.lamposhka.quizer.Task;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
class TextTask implements Task {
    private String text;
    private String answer;
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
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
        if (this.answer.equals(answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}