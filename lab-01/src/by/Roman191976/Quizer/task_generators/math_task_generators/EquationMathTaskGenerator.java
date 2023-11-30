package by.Roman191976.Quizer.task_generators.math_task_generators;

import java.util.EnumSet;

import by.Roman191976.Quizer.tasks.math_tasks.*;
import by.Roman191976.Quizer.tasks.math_tasks.MathTask.Operation;;

public class EquationMathTaskGenerator extends AbstractMathGenerator {
    public EquationMathTaskGenerator(int minNumber, int maxNumber,
            EnumSet<by.Roman191976.Quizer.tasks.math_tasks.MathTask.Operation> operations) {
        super(minNumber, maxNumber, operations);
    }

    boolean xOnFirstPosition;


    @Override
    public EquationMathTask generate() {
        int num1;
        int num2;
        Operation operator;
        xOnFirstPosition = random.nextBoolean();

        if (xOnFirstPosition) {
            operator = generateRandomOperator();    
            if (operator.equals(Operation.MULTIPLICATION) | operator.equals(Operation.DIVISION)) { 
                num1 = generateRandomNumberExceptZero();
                num2 = generateRandomNumber();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        } else {
            operator = generateRandomOperator();    
            if (operator.equals(Operation.MULTIPLICATION) | operator.equals(Operation.DIVISION)) { 
                num1 = generateRandomNumber();
                num2 = generateRandomNumberExceptZero();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        }

        int answer = calculateAnswer(num1, num2, operator);
        if (operator == Operation.DIVISION) {
            if (xOnFirstPosition) {
                num2 = answer * num1;
            } else {       
                num1 = answer * num2;
            }
        }
        String taskText = generateTaskText(num1, num2, operator, xOnFirstPosition);

        return new EquationMathTask(taskText, answer);
    }

    private int calculateAnswer(int num1, int num2, Operation operator) {
        switch (operator) {
            case SUM:
                return num1 + num2;
            case DIFFERENCE:
                if (xOnFirstPosition) return num2 - num1;
                return num1 - num2;
            case MULTIPLICATION:
                return num1 * num2;
            case DIVISION:
                if (xOnFirstPosition) return num2 / num1;
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private String generateTaskText(int num1, int num2, Operation operator, boolean xOnFirstPosition) {
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
    
        if (xOnFirstPosition) {
            return "x " + operatorSymbol + " " + num2 + " = " + num1;
        } else {
            return num1 + " " + operatorSymbol + " x = " + num2;
        }
    }
}
