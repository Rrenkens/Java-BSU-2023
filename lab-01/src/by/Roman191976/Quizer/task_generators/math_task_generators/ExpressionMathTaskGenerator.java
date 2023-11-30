package by.Roman191976.Quizer.task_generators.math_task_generators;

import java.util.EnumSet;

import by.Roman191976.Quizer.tasks.math_tasks.*;
import by.Roman191976.Quizer.tasks.math_tasks.MathTask.Operation;

public class ExpressionMathTaskGenerator extends AbstractMathGenerator {
    public ExpressionMathTaskGenerator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
        super(minNumber, maxNumber, operations);
    }

    @Override
    public ExpressionMathTask generate() {
        Operation operator = generateRandomOperator();
        int num1 = generateRandomNumber();
        int num2;
        if (operator == Operation.DIVISION) {
            num2 = generateRandomNumberExceptZero();
        } else {
            num2 = generateRandomNumber();
        }

        int answer = calculateAnswer(num1, num2, operator);
        if (operator == Operation.DIVISION) {
            num1 = answer * num2;
        }
        String taskText = generateTaskText(num1, num2, operator);

        return new ExpressionMathTask(taskText, answer);
    }

    private int calculateAnswer(int num1, int num2, Operation operator) {
        switch (operator) {
            case SUM:
                return num1 + num2;
            case DIFFERENCE:
                return num1 - num2;
            case MULTIPLICATION:
                return num1 * num2;
            case DIVISION:
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private String generateTaskText(int num1, int num2, Operation operator) {
        String operatorSymbol;
        switch (operator) {
            case SUM:
                operatorSymbol = "+";
                break;
            case DIFFERENCE:
                operatorSymbol = "-";
                break;
            case MULTIPLICATION:
                operatorSymbol = "*";
                break;
            case DIVISION:
                operatorSymbol = "/";
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        return num1 + " " + operatorSymbol + " " + num2 + " =";
    }
}