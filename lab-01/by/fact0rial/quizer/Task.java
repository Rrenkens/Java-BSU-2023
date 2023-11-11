package by.fact0rial.quizer
/**
 * Interface, который описывает одно задание
 */
public interface Task {
    private String text;
    /**
     @return текст задания
     */
    public String getText();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);
}