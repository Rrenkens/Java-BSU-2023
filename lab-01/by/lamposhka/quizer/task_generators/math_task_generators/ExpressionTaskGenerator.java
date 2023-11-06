package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.ExpressionTask;
import by.lamposhka.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {

    /**
     * @param minNumber       минимальное число
     * @param maxNumber       максимальное число
     * @param validOperations {@link EnumSet} с допустимыми операциями
     */

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> validOperations
    ) {
        super(minNumber, maxNumber, validOperations);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        String text;
        int answer;
        int number1 = generateNum();
        int number2 = generateNum();

        switch (generateOperator()) {
            case SUM:
                answer = number1 + number2;
                text = number1 + "+" + number2;
                break;
            case DIFFERENCE:
                answer = number1 - number2;
                text = number1 + "-" + number2;
                break;
            case MULTIPLICATION:
                answer = number1 * number2;
                text = number1 + "*" + number2;
                break;
            case DIVISION:
                while (number2 == 0) {
                    number2 = generateNum();
                }
                answer = number1 / number2;
                text = number1 + "/" + number2;
                break;
            default:
                answer = number1;
                text = number1 + "";
        }
        return new ExpressionTask(text, answer);
    }
}