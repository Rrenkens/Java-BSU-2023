package by.Roman191976.Quizer.task_generators.math_task_generators;

import java.util.Random;

import by.Roman191976.Quizer.task_generators.MathTaskGenerator;

public abstract class AbstractMathGenerator implements MathTaskGenerator {
    private int minNumber;
    private int maxNumber;
    private boolean generateSum;
    private boolean generateDifference;
    private boolean generateMultiplication;
    private boolean generateDivision;
    public Random random;

    public AbstractMathGenerator(
        int minNumber,
        int maxNumber,
        boolean generateSum,
        boolean generateDifference,
        boolean generateMultiplication,
        boolean generateDivision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            if (getDiffNumber() < 0) {
                throw new IllegalArgumentException("нижняя граница больше верхней");
            }
            this.generateSum = generateSum;
            this.generateDifference = generateDifference;
            this.generateMultiplication = generateMultiplication;
            this.generateDivision = generateDivision;
            this.random = new Random();
        }

        public String generateRandomOperator() {
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

        
    public int generateRandomNumber() {
        return random.nextInt(maxNumber - minNumber + 1) + minNumber;
    }

    public int generateRandomNumberExceptZero() {
        int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        return number == 0 ? number + 1 : number;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
}
