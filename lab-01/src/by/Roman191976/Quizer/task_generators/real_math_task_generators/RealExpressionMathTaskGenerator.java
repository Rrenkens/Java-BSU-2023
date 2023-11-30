package by.Roman191976.Quizer.task_generators.real_math_task_generators;

import java.util.EnumSet;

import by.Roman191976.Quizer.tasks.real_math_tasks.RealExpressionMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask.Operation;

public class RealExpressionMathTaskGenerator extends AbstractRealMathTaskGenerator {
    private Operation operation;
    private int precision;

    public RealExpressionMathTaskGenerator(double minNumber, double maxNumber, EnumSet<Operation> operations, int precision) {
        super(minNumber, maxNumber, operations, precision);
        this.operation = generateRandomOperator();
        this.precision = precision;
    }

    @Override
    public RealExpressionMathTask generate() {
        double num1 = generateRandomNumber();
        double num2 = generateRandomNumber();
        double num3 = generateRandomNumber();
        double answer = calculateAnswer(num1, num2, num3, operation);
        answer = round(answer, precision);
        String taskText = generateTaskText(num1, num2, num3, operation);

        return new RealExpressionMathTask(taskText, answer);
    }

    private double calculateAnswer(double num1, double num2, double num3, RealMathTask.Operation operation) {
        switch (operation) {
            case SUM:
                return num1 + num2 + num3;
            case DIFFERENCE:
                return num1 - num2 - num3;
            case MULTIPLICATION:
                return num1 * num2 * num3;
            case DIVISION:
                return num1 / num2 / num3;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operation);
        }
    }

    private String generateTaskText(double num1, double num2, double num3, RealMathTask.Operation operation) {
        String operatorSymbol;
        switch (operation) {
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
                throw new IllegalArgumentException("Invalid operator: " + operation);
        }

        return "(" + num1 + " " + operatorSymbol + " " + num2 + ") " + operatorSymbol + " " + num3;
    }
}