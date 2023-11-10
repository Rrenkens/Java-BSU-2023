package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Result;
import by.ullliaa.quizer.by.ullliaa.quizer.Task;

import java.util.Objects;

public class ExpressionTask implements Task{
    private final int firstNumber;
    private final int secondNumber;
    private final String operation_;
    private String task;
    private double answer_;

    public ExpressionTask(int minNumber,
                          int maxNumber,
                          String operation) {
        firstNumber = minNumber;
        secondNumber = maxNumber;
        operation_ = operation;
        getAnswer();
    }
    /**
     @return текст задания
     */
    public String getText() {
        return String.valueOf(firstNumber) + operation_ + String.valueOf(secondNumber) + "=?";
    };

    public void getAnswer() {
        if (Objects.equals(operation_, "+")) {
            answer_ = firstNumber + secondNumber;
        } else if (Objects.equals(operation_, "-")) {
            answer_ = firstNumber - secondNumber;
        } else if (Objects.equals(operation_, "*")) {
            answer_ = firstNumber * secondNumber;
        } else if (Objects.equals(operation_, "/")) {
            answer_ = ((double) firstNumber) / secondNumber;
        }
    }
    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    public Result validate(String answer) {
        try {
            double num = Double.parseDouble(answer);
            if (Math.abs(num - answer_) < 0.000001){
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e){
            return Result.INCORRECT_INPUT;
        }
    };
}
