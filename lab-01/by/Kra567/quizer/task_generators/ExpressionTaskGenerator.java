package by.Kra567.quizer.task_generators;

import by.Kra567.quizer.basics.TaskGenerator;
import by.Kra567.quizer.tasks.ExpressionTask;
import by.Kra567.quizer.tasks.OperationType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ExpressionTaskGenerator implements TaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    private int minNumber;
    private int maxNumber;
    private List<OperationType> operations = new ArrayList<>();
    private Random gen = new Random();

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (generateSum){
            operations.add(OperationType.SUM);
        }
        if (generateDifference){
            operations.add(OperationType.DIFF);
        }
        if (generateDivision){
            operations.add(OperationType.DIV);
        }
        if (generateMultiplication){
            operations.add(OperationType.MUL);
        }
    }

    private int generateArgument() {
        return minNumber + gen.nextInt(maxNumber - minNumber + 1);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() throws Exception {
        int idx = gen.nextInt(operations.size());
        return new ExpressionTask(operations.get(idx),generateArgument(),generateArgument());
    }
}
