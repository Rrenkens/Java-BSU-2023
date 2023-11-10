package by.Roman191976.Quizer.task_generators.math_task_generators;

import by.Roman191976.Quizer.tasks.math_tasks.*;;

public class EquationMathTaskGenerator extends AbstractMathGenerator {
    boolean xOnFirstPosition;
    public EquationMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference,
            boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
        //TODO Auto-generated constructor stub
    }

    @Override
    public EquationMathTask generate() {
        int num1;
        int num2;
        String operator;
        xOnFirstPosition = random.nextBoolean();

        if (xOnFirstPosition) {
            operator = generateRandomOperator();    
            if (operator.equals("*") | operator.equals("/")) { 
                num1 = generateRandomNumberExceptZero();
                num2 = generateRandomNumber();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        } else {
            operator = generateRandomOperator();    
            if (operator.equals("*") | operator.equals("/")) { 
                num1 = generateRandomNumber();
                num2 = generateRandomNumberExceptZero();
            } else {
                num1 = generateRandomNumber();
                num2 = generateRandomNumber();
            }
        }

        int answer = calculateAnswer(num1, num2, operator);
        String taskText = generateTaskText(num1, num2, operator, xOnFirstPosition);

        return new EquationMathTask(taskText, answer);
    }

    private int calculateAnswer(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num2 - num1;
            case "-":
                if (xOnFirstPosition) return num2 + num1;
                return num1 - num2;
            case "*":
                return num2 / num1;
            case "/":
                if (xOnFirstPosition) return num1 * num2;
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private String generateTaskText(int num1, int num2, String operator, boolean xOnFirstPosition) {
        if (xOnFirstPosition) {
            return "x " + operator + " " + num2 + " = " + num1;
        } else {
            return num1 + " " + operator + " x = " + num2;
        }
    }
}
