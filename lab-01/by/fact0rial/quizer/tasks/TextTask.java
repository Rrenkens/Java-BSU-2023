package by.fact0rial.quizer.tasks
/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    private String txt;
    private String ans;
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    TextTask(
            String text,
            String answer
    ) {
        this.ans = answer;
        this.txt = text;
    }

    // ...
}