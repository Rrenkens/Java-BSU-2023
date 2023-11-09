package by.Lexus_FAMCS.quizer.task_generators.math_task_generators;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    public ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> operations
    ) {
        super(minNumber, maxNumber, operations);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        int num1 = generateInteger(getMinNumber(), getMaxNumber());
        int num2 = generateInteger(getMinNumber(), getMaxNumber());
        Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
        double result = Double.NaN;
        switch (operator) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = generateResultOfDivision(num1, num2);
        }
        return new ExpressionTask("" + num1 + operator + num2 + "=?", result);
    }

}
