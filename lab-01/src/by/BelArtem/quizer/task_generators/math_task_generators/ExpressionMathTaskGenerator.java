package by.BelArtem.quizer.task_generators.math_task_generators;

import by.BelArtem.quizer.exceptions.ExpressionTaskGeneratorException;
import by.BelArtem.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.Random;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    public ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication,
                generateDivision);
    }

    @Override
    public ExpressionMathTask generate() throws Exception {
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
        int firstNum = random.nextInt(this.getDiffNumber() + 1) + minNumber;
        int secondNum = random.nextInt(this.getDiffNumber() + 1) + minNumber;
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
                    secondNum = random.nextInt(this.getDiffNumber() + 1) + minNumber;
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

        return new ExpressionMathTask(text.toString(),answer);
    }
}
