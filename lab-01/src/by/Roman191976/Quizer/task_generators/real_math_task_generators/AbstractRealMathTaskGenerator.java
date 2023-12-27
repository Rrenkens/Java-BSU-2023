package by.Roman191976.Quizer.task_generators.real_math_task_generators;

import by.Roman191976.Quizer.tasks.real_math_tasks.RealMathTask.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public abstract class AbstractRealMathTaskGenerator implements RealMathTaskGenerator {
    private double minNumber;
    private double maxNumber;
    private Operation[] AvailibleOperations;
    private int precision;
    public Random random;

    public AbstractRealMathTaskGenerator(
            double minNumber,
            double maxNumber,
            Operation[] operations,
            int precision) {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("нижняя граница больше верхней");
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.AvailibleOperations = operations;
        this.precision = precision;
        this.random = new Random();
    } 

    public Operation generateRandomOperator() {
        int index = random.nextInt(AvailibleOperations.length);
        return AvailibleOperations[index];
    }

    public double generateRandomNumber() {
        return round((random.nextDouble() * getDiffNumber() + minNumber), precision);
    }

    public double generateRandomNumberExceptZero() {
        double number = round(random.nextDouble() * getDiffNumber() + minNumber, precision);
        return (Double.compare(number, 0) == 0 ? 1 : number);
    }

    @Override
    public double getMinNumber() {
        return minNumber;
    }

    @Override
    public double getMaxNumber() {
        return maxNumber;
    }

    public double round(double value, int precision) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(precision, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
