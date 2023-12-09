package by.BelArtem.quizer.task_generators.math_task_generators;

import by.BelArtem.quizer.exceptions.EquationTaskGeneratorException;
import by.BelArtem.quizer.tasks.math_tasks.*;

import java.util.Random;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    public EquationMathTaskGenerator(
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
    public EquationMathTask generate() throws Exception{
        StringBuilder operators = new StringBuilder();
        int x;
        StringBuilder text = new StringBuilder();

        if (this.generateSum) {
            operators.append("+");
        }
        if (this.generateDifference) {
            operators.append("-");
        }
        if (this.generateMultiplication && (maxNumber != 0 || minNumber != 0 )) {
            operators.append("*");
        }
        if (this.generateDivision && (maxNumber != 0 || minNumber != 0 )) {
            operators.append("/");
        }

        if (operators.isEmpty()){
            throw new EquationTaskGeneratorException("Can't generate meaningful expression(");
        }

        Random random = new Random();
        int num1 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
        int num2 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
        int operationIndex = random.nextInt(operators.length());
        String operation = operators.substring(operationIndex, operationIndex + 1);
        boolean type = random.nextBoolean();

        /**
         * If {type} equals to zero, EquationGenerator will generate an equation of following form:
         *     <num1><operator>x=<answer>.
         * Otherwise, it will generate an equation of x<operator><num2>=<answer> form.
         */

        int answer;
        switch (operation){
            case "+": {
                answer = num1 + num2;
                if (type) {
                    text.append("x + ").append(num2).append(" = ").append(answer);
                    x = num1;
                } else {
                    text.append(num1).append(" + x = ").append(answer);
                    x = num2;
                }
                break;
            }
            case "-": {
                answer = num1 - num2;
                if (type) {
                    text.append("x - ").append(num2).append(" = ").append(answer);
                    x = num1;
                } else {
                    text.append(num1).append(" - x = ").append(answer);
                    x = num2;
                }
                break;
            }
            case "*": {
                if (type) {
                    while (num2 == 0){
                        num2 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
                    }
                    answer = num1 * num2;
                    text.append("x * ").append(num2).append(" = ").append(answer);
                    x = num1;
                } else {
                    while (num1 == 0){
                        num1 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
                    }
                    answer = num1 * num2;
                    text.append(num1).append(" * x = ").append(answer);
                    x = num2;
                }
                break;
            }
            case "/": {
                if (type) {
                    while (num2 == 0){
                        num2 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
                    }
                    answer = num1 / num2;
                    num1 = num2 * answer;

                    text.append("x / ").append(num2).append(" = ").append(answer);
                    x = num1;
                } else {
                    while (num2 == 0 || num1 / num2 == 0) {
                        num1 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
                        num2 = random.nextInt(this.getDiffNumber() + 1) + minNumber;
                    }
                    answer = num1 / num2;
                    num1 = num2 * answer;
                    text.append(num1).append(" / x = ").append(answer);
                    x = num2;
                }
                break;
            }
            default:{
                throw new EquationTaskGeneratorException("Unexpected error happened while tried " +
                        "to generate expression");
            }
        }

        return new EquationMathTask(text.toString(), x);
    }
}
