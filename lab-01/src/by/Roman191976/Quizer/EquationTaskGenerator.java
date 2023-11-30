package by.Roman191976.Quizer;

import java.util.Random;

class EquationTaskGenerator implements TaskGenerator {
    private int minNumber;
    private int maxNumber;
    private boolean generateSum;
    private boolean generateDifference;
    private boolean generateMultiplication;
    private boolean generateDivision;
    private Random random;
    boolean xOnFirstPosition;

    EquationTaskGenerator(
        int minNumber,
        int maxNumber,
        boolean generateSum,
        boolean generateDifference,
        boolean generateMultiplication,
        boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.generateSum = generateSum;
        this.generateDifference = generateDifference;
        this.generateMultiplication = generateMultiplication;
        this.generateDivision = generateDivision;
        this.random = new Random();
    }

    @Override
    public EquationTask generate() {
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

 // for x in second position
            num1 = (num1 / num2 * num2 > minNumber) ? (num1 / num2 * num2) : ((num1 + num2)/ num2 * num2);

        int answer = calculateAnswer(num1, num2, operator);

        if (xOnFirstPosition) {
            // swap answer and num1
            num1 = num1 + answer;
            answer = num1 - answer;
            num1 = num1 - answer;
        }

        String taskText = generateTaskText(num1, num2, operator, xOnFirstPosition);

        return new EquationTask(taskText, answer);
    }

    private int generateRandomNumber() {
        return random.nextInt(maxNumber - minNumber + 1) + minNumber;
    }

    private int generateRandomNumberExceptZero() {
        int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        return number == 0 ? number + 1 : number;
    }

    private String generateRandomOperator() {
        StringBuilder operators = new StringBuilder();
        if (generateSum) {
            operators.append("+");
        }
        if (generateDifference) {
            operators.append("-");
        }
        if (generateMultiplication) {
            operators.append("*");
        }
        if (generateDivision) {
            operators.append("/");
        }

        int index = random.nextInt(operators.length());
        return String.valueOf(operators.charAt(index));
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
                // if (xOnFirstPosition) return num1 * num2;
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