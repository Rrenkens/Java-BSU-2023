package tasks;
public interface Task {
    /**
     @return текст задания
     */
    String getText();
    
    boolean equals(Task other);
    
    boolean IsCorrect(String ans);
     /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);

	String getAns();

}


