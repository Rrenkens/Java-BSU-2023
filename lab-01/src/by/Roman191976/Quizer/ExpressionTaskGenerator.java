package by.Roman191976.Quizer;

import java.util.Random;

class ExpressionTaskGenerator implements TaskGenerator {
    private int minNumber;
    private int maxNumber;
    private boolean generateSum;
    private boolean generateDifference;
    private boolean generateMultiplication;
    private boolean generateDivision;
    private Random random;

    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    ExpressionTaskGenerator(
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
    
    /**
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public Task generate() {
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

        return new ExpressionTask(taskText, answer);
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