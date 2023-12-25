package by.Kra567.quizer.task_generators;

import by.Kra567.quizer.basics.TaskGenerator;
import by.Kra567.quizer.tasks.EquationTask;
import by.Kra567.quizer.tasks.ExpressionTask;
import by.Kra567.quizer.tasks.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTaskGenerator implements TaskGenerator {
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
    public EquationTaskGenerator(
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

    /**
     * return задание типа {@link EquationTask}
     */
    private int generateArgument() {
        return minNumber + gen.nextInt(maxNumber - minNumber + 1);
    }
    private int generateArgumentNonZero() {
        int res = generateArgument();
        if (res == 0){
            return generateArgumentNonZero();
        }
        return res;
    }
    /**
     * return задание типа {@link ExpressionTask}
     */
    public EquationTask generate() throws Exception {
        int idx = gen.nextInt(operations.size());
        //System.out.println("{"+Integer.valueOf(idx).toString() + "}\n");
        return new EquationTask(operations.get(idx),gen.nextBoolean(),generateArgument(),generateArgument());
    }
}
