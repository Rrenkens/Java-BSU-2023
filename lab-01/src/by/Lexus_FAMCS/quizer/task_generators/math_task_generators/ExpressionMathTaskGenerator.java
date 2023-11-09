package by.Lexus_FAMCS.quizer.task_generators.math_task_generators;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;
public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        int num1 = generateInteger(getMinNumber(), getMaxNumber());
        int num2 = generateInteger(getMinNumber(), getMaxNumber());
        Character operator = permittedSymbols.get(generateInteger(0, permittedSymbols.size()));
        String result;
        switch (operator) {
            case '+' -> result = Integer.toString(num1 + num2);
            case '-' -> result = Integer.toString(num1 - num2);
            case '*' -> result = Integer.toString(num1 * num2);
            case '/' -> result = num1 + "/" + num2;
            default -> result = "";
        }
        return new ExpressionTask("" + num1 + operator + num2 + "=?", result);
    }

}
