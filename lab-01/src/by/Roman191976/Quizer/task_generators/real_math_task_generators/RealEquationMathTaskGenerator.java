package by.Roman191976.Quizer.task_generators.real_math_task_generators;

import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealEquationMathTask;
import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask.Operation;

public class RealEquationMathTaskGenerator extends AbstractRealMathTaskGenerator {
    private Operation operation;
    private boolean xOnFirstPosition;
    private int precision;

    public RealEquationMathTaskGenerator(double minNumber, double maxNumber,  Operation[] operations, int precision) {
        super(minNumber, maxNumber, operations, precision);
        this.precision = precision;
    }

    @Override
    public RealEquationMathTask generate() {
        double num1;
        double num2;
        double answer;
        this.operation = generateRandomOperator();
       
        xOnFirstPosition = random.nextBoolean();

        if (xOnFirstPosition) {  
            if (operation.equals(Operation.MULTIPLICATION) | operation.equals(Operation.DIVISION)) { 
                num1 = generateRandomNumberExceptZero();
                num2 = generateRandomNumber();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        } else {    
            if (operation.equals(Operation.MULTIPLICATION) | operation.equals(Operation.DIVISION)) { 
                num1 = generateRandomNumberExceptZero();
                num2 = generateRandomNumberExceptZero();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        }

        answer = calculateAnswer(num1, num2, operation);
        answer = round(answer, precision);
        String taskText = generateTaskText(num1, num2, operation, xOnFirstPosition);

        return new RealEquationMathTask(taskText, answer);
    }

    private double calculateAnswer(double num1, double num2, Operation operator) {
        switch (operator) {
            case SUM:
                return num2 - num1;
            case DIFFERENCE:
                if (xOnFirstPosition) return num2 + num1;
                return num1 - num2;
            case MULTIPLICATION:
                return num2 / num1;
            case DIVISION:
                if (xOnFirstPosition) return num2 * num1;
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private String generateTaskText(double num1, double num2, RealMathTask.Operation operation, boolean xOnFirstPosition) {
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

        if (xOnFirstPosition) {
            return "x " + operatorSymbol + " " + num2 + " = " + num1;
        } else {
            return num1 + " " + operatorSymbol + " x = " + num2;
        }
    }
}
