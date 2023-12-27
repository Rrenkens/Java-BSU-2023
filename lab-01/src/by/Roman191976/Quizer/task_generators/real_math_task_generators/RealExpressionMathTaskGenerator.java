package by.Roman191976.Quizer.task_generators.real_math_task_generators;


import by.Roman191976.Quizer.tasks.real_math_tasks.RealExpressionMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask.Operation;

public class RealExpressionMathTaskGenerator extends AbstractRealMathTaskGenerator {
    private Operation operation;
    private int precision;

    public RealExpressionMathTaskGenerator(double minNumber, double maxNumber,  Operation[] operations, int precision) {
        super(minNumber, maxNumber, operations, precision);
        this.precision = precision;
    }

    @Override
    public RealExpressionMathTask generate() {
        this.operation = generateRandomOperator();
        double num1 = generateRandomNumber();
        double num2;
        if (operation == Operation.DIVISION) {
            num2 = generateRandomNumberExceptZero();
        } else {
            num2 = generateRandomNumber();
        }

        double answer = calculateAnswer(num1, num2, operation);
        answer = round(answer, precision);
        String taskText = generateTaskText(num1, num2, operation);

        return new RealExpressionMathTask(taskText, answer);
    }

    private double calculateAnswer(double num1, double num2,RealMathTask.Operation operation) {
        switch (operation) {
            case SUM:
                return num1 + num2;
            case DIFFERENCE:
                return num1 - num2;
            case MULTIPLICATION:
                return num1 * num2;
            case DIVISION:
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operation);
        }
    }

    private String generateTaskText(double num1, double num2, RealMathTask.Operation operation) {
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

        return num1 + " " + operatorSymbol + " " + num2 + " = ";
    }
}