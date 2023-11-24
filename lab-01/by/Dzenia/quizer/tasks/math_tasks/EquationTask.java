package by.Dzenia.quizer.tasks.math_tasks;

import by.Dzenia.quizer.Operation;

public class EquationTask extends AbstractMathTask {

    public enum TypeOfEquationTask {
        LEFT_VARIABLE,
        RIGHT_VARIABLE
    }

    public EquationTask(double number,
                        double result,
                        Operation operation,
                        TypeOfEquationTask type,
                        int precision)
    {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision cannot be negative");
        }
        this.precision = precision;
        if (type == TypeOfEquationTask.LEFT_VARIABLE) {
            switch (operation) {
                case SUM -> {
                    taskText = "x + " + number + " = " + result;
                    answer = result - number;
                }
                case DIFFERENCE -> {
                    taskText = "x - " + number + " = " + result;
                    answer = result + number;
                }
                case MULTIPLICATION -> {
                    if (number == 0) {
                         throw new ArithmeticException("Have a zero division");
                    }
                    taskText = "x * " + number + " = " + result;
                    answer = result / number;
                }
                case DIVISION -> {
                    if (number == 0) {
                        throw new ArithmeticException("Have a zero division");
                    }
                    taskText = "x / " + number + " = " + result;
                    answer = result * number;
                }
            }
            return;
        }
        switch (operation) {
            case SUM -> {
                taskText = number + " + x = " + result;
                answer = result - number;
            }
            case DIFFERENCE -> {
                taskText =  number + " - x = " + result;
                answer = number - result;
            }
            case MULTIPLICATION -> {
                if (number == 0) {
                    throw new ArithmeticException("Have a zero division");
                }
                taskText = number + " * x = " + result;
                answer = result / number;
            }
            case DIVISION -> {
                if (result == 0) {
                    throw new ArithmeticException("Have a zero division");
                }
                taskText =  number + " / x = " + result;
                answer = number / result;
            }
        }
    }
}
