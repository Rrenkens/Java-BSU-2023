package by.Lexus_FAMCS.quizer.task_generators.math_task_generators;

import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    EquationMathTaskGenerator(
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
    public EquationTask generate() {
        int num = generateInteger(getMinNumber(), getMaxNumber());
        int answer = generateInteger(getMinNumber(), getMaxNumber());
        Character operator = permittedSymbols.get(generateInteger(0, permittedSymbols.size()));
        double result = Double.NaN;
        boolean reverse = Math.random() > 0.5; // reverse is num<op>x=answer
        switch (operator) {
            case '+' -> result = answer - num;
            case '-' -> result = reverse ? num - answer : num + answer;
            case '*' -> result = (double) answer / num;
            case '/' -> result = reverse ? (double) num / answer : num * answer;
        }
        return new EquationTask("" + (reverse ? num : "x") + operator +
                (reverse ? "x" : num) + "=" + answer, result);
    }

}
