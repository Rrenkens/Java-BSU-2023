package by.Roman191976.Quizer.task_generators.math_task_generators;

import by.Roman191976.Quizer.tasks.math_tasks.*;;

public class ExpressionMathTaskGenerator extends AbstractMathGenerator {

    public ExpressionMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference,
            boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
        //TODO Auto-generated constructor stub
    }


  @Override
    public ExpressionMathTask generate() {
        String operator = generateRandomOperator();
        int num1 = generateRandomNumber();
        int num2;
        if (operator.equals("/")) {
            num2 = generateRandomNumberExceptZero();
        } else {
           num2 = generateRandomNumber();
        }

        int answer = calculateAnswer(num1, num2, operator);
        String taskText = generateTaskText(num1, num2, operator);

        return new ExpressionMathTask(taskText, answer);
    }
    
    private int calculateAnswer(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private String generateTaskText(int num1, int num2, String operator) {
        return num1 + " " + operator + " " + num2 +  " =";
    }
}
