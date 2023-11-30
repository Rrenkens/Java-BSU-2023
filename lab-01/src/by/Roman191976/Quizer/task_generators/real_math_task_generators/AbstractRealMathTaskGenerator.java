package by.Roman191976.Quizer.task_generators.real_math_task_generators;

import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractRealMathTaskGenerator implements RealMathTaskGenerator {
    private double minNumber;
    private double maxNumber;
    private EnumSet<Operation> operations;
    private int precision;
    public Random random;

    public AbstractRealMathTaskGenerator(
            double minNumber,
            double maxNumber,
            EnumSet<Operation> operations,
            int precision) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("нижняя граница больше верхней");
        }
        this.operations = operations;
        this.precision = precision;
        this.random = new Random();
    } 

    public Operation generateRandomOperator() {
        Operation[] availableOperations = operations.toArray(new Operation[0]);
        int index = random.nextInt(availableOperations.length);
        return availableOperations[index];
    }

    public double generateRandomNumber() {
        return round((random.nextDouble() * (maxNumber - minNumber) + minNumber), precision);
    }

    public double generateRandomNumberExceptZero() {
        double number = random.nextDouble() * (maxNumber - minNumber) + minNumber;
        return round((number == 0 ? number + 1 : number), precision);
    }

    @Override
    public double getMinNumber() {
        return minNumber;
    }

    @Override
    public double getMaxNumber() {
        return maxNumber;
    }

    @Override
    public double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }

    public double round(double value, int precision) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(precision, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
