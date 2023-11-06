package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;
import by.lamposhka.quizer.tasks.math_tasks.EquationTask;
import by.lamposhka.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class EquationTaskGenerator extends AbstractMathTaskGenerator {

    /**
     * @param minNumber  минимальное число
     * @param maxNumber  максимальное число
     * @param validOperations {@link EnumSet} с допустимыми операциями
     */
    public EquationTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> validOperations
    ) {
        super(minNumber, maxNumber, validOperations);
    }

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        String text;
        int answer;
        int number1 = generateNum();
        int number2 = generateNum();
        boolean xFirstPositionIndicator = generateVariablePositionIndicator();
        switch (generateOperator()) {
            case SUM:
                text = (xFirstPositionIndicator) ? "x + " + number1 + " = " + number2 : number1 + " + x" + " = " + number2;
                answer = number2 - number1;
                break;
            case DIFFERENCE:
                text = (xFirstPositionIndicator) ? "x - " + number1 + " = " + number2 : number1 + " - x" + " = " + number2;
                answer = (xFirstPositionIndicator) ? number1 + number2 : number1 - number2;
                break;
            case MULTIPLICATION:
                text = (xFirstPositionIndicator) ? "x * " + number1 + " = " + number2 : number1 + " * x" + " = " + number2;
                answer = number2 / number1;
                break;
            case DIVISION:
                text = (xFirstPositionIndicator) ? "x / " + number1 + " = " + number2 : number1 + " / x" + " = " + number2;
                answer = (xFirstPositionIndicator) ? number1 * number2 : number1 / number2;
                break;
            default:
                text = "x = " + number1;
                answer = number1;
                break;
        }

        return new EquationTask(text, answer);
    }
}