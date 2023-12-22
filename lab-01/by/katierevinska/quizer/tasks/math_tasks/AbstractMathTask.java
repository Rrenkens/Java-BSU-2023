package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public abstract class AbstractMathTask implements MathTask {

    public abstract static class Generator implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;

        public double getMinNumber() {
            return minNumber;
        }

        public double getMaxNumber() {
            return maxNumber;
        }

        public String buildExpression(Operation operation, String num1, String num2, String answer) {
            StringBuilder expression = new StringBuilder();
            return expression.append(num1)
                    .append(operation.toString())
                    .append(num2)
                    .append("=")
                    .append(answer)
                    .toString();
        }

        public static Double generatingDoubleWithPrecision(double minNumber, double maxNumber, int precision) {
            double num1 = ((int) ThreadLocalRandom.current().nextDouble(minNumber, maxNumber + 1) * pow(10, precision)) * 1.0 / pow(10, precision);

            return num1;
        }

        public static Double generationWithout0(double minNumber, double maxNumber, int precision) {
            int randomInt = ThreadLocalRandom.current().nextInt((int) (minNumber * Math.pow(10, precision)), (int) (maxNumber * Math.pow(10, precision)));
            Double randomDouble = randomInt / Math.pow(10, precision);
            if (randomDouble == 0.0) {
                randomDouble += Math.pow(10, -precision);
            }
            return randomDouble;
        }

        public static String formationWithBracket(Double num) {
            return num < 0 ? "(" + num + ")" : String.valueOf(num);
        }
    }

    protected String text;
    protected String answer;

    protected int precision;


    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        answer = answer.replace(',', '.');
        this.answer = this.answer.replace(',', '.');
        try {
            Double.parseDouble(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }

        if (abs(Double.parseDouble(this.answer) - Double.parseDouble(answer)) < pow(0.1, precision)) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
