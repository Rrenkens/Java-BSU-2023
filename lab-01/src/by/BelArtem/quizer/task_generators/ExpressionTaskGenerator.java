package by.BelArtem.quizer.task_generators;

import by.BelArtem.quizer.TaskGenerator;
import by.BelArtem.quizer.tasks.ExpressionTask;
import by.BelArtem.quizer.exceptions.*;

import java.util.Random;

public class ExpressionTaskGenerator implements TaskGenerator{

    int minNumber;
    int maxNumber;
    boolean generateSum;
    boolean generateDifference;
    boolean generateMultiplication;
    boolean generateDivision;

    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("minNumber is greater than maxNumber!");
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.generateSum = generateSum;
        this.generateDifference = generateDifference;
        this.generateMultiplication = generateMultiplication;
        this.generateDivision = generateDivision;
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public ExpressionTask generate() throws Exception {
        StringBuilder operators = new StringBuilder();
        int answer;
        StringBuilder text = new StringBuilder();

        if (this.generateSum) {
            operators.append("+");
        }
        if (this.generateDifference) {
            operators.append("-");
        }
        if (this.generateMultiplication) {
            operators.append("*");
        }
        if (this.generateDivision && (maxNumber != 0 || minNumber != 0 )) {
            operators.append("/");
        }

        if (operators.isEmpty()){
            throw new ExpressionTaskGeneratorException("Can't generate meaningful expression(");
        }

        Random random = new Random();
        int firstNum = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int secondNum = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int operationIndex = random.nextInt(operators.length());
        String operation = operators.substring(operationIndex, operationIndex + 1);

        switch (operation){
            case "+": {
                text.append(firstNum).append(" + ").append(secondNum).append(" = ?");
                answer = firstNum + secondNum;
                break;
            }
            case "-": {
                text.append(firstNum).append(" - ").append(secondNum).append(" = ?");
                answer = firstNum - secondNum;
                break;
            }
            case "*": {
                text.append(firstNum).append(" * ").append(secondNum).append(" = ?");
                answer = firstNum * secondNum;
                break;
            }
            case "/": {
                while (secondNum == 0) {
                    secondNum = random.nextInt(maxNumber - minNumber + 1) + minNumber;
                }
                answer = firstNum / secondNum;
                firstNum = secondNum * answer;
                text.append(firstNum).append(" / ").append(secondNum).append(" = ?");
                break;
            }
            default:{
                throw new ExpressionTaskGeneratorException("Unexpected error happened while tried " +
                        "to generate expression");
            }
        }

        return new ExpressionTask(text.toString(),answer);
    }
}
