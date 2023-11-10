package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Result;
import by.ullliaa.quizer.by.ullliaa.quizer.Task;

import java.util.Objects;

public class EquationTask implements Task {
    private final double minimum;
    private final double maximum;
    private final String operation_;
    private final int position;
    private String task;
    private double answer_;


    public EquationTask(double minNumber,
                        double maxNumber,
                        String operation,
                        int position) {
        minimum = minNumber;
        maximum = maxNumber;
        operation_ = operation;
        this.position = position;
        getAnswer();
    }

    /**
     * @return текст задания
     */
    public String getText() {
        if (position == 1) {
            task = 'x' + operation_ + String.valueOf(minimum) + '=' + String.valueOf(maximum);
        } else {
            task = String.valueOf(minimum) + operation_ + 'x' + '=' + String.valueOf(maximum);
        }
        return task;
    }

    public void getAnswer() {
        if (position == 1) {
            if (Objects.equals(operation_, "+")) {
                answer_ = maximum - minimum;
            } else if (Objects.equals(operation_, "-")) {
                answer_ = maximum + minimum;
            } else if (Objects.equals(operation_, "*")) {
                answer_ = ((double) maximum) / minimum;
            } else if (Objects.equals(operation_, "/")) {
                answer_ = maximum * minimum;
            }
        } else if (position == 0) {
            if (Objects.equals(operation_, "+")) {
                answer_ = maximum - minimum;
            } else if (Objects.equals(operation_, "-")) {
                answer_ = minimum - maximum;
            } else if (Objects.equals(operation_, "*")) {
                answer_ = ((double) maximum) / minimum;
            } else if (Objects.equals(operation_, "/")) {
                answer_ = minimum / ((double) maximum);
            }
        }
    }
    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param answer ответ на задание
     * @return результат ответа
     * @see Result
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
    }
}
