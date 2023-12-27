package by.Dzenia.quizer.task_generators.math_task_generators;
import by.Dzenia.quizer.Operation;
import java.util.EnumSet;
import java.util.Random;
public abstract class AbstractMathGenerator implements MathTaskGenerator {
    protected double minNumber;
    protected double maxNumber;
    protected int precision;
    protected EnumSet<Operation> includedOperations;

    public AbstractMathGenerator(
            double minNumber,
            double maxNumber,
            int precision,
            EnumSet<Operation> includedOperations
    ) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision cannot be negative");
        }
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("MIN_NUMBER cannot be greater tha MAX_NUMBER");
        }
        if (includedOperations.isEmpty()) {
            return;
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.precision = precision;
        this.includedOperations = includedOperations;
    }
    public static double truncateDouble(double number, int precision) {
        double multiplier = Math.pow(10, precision);
        return Math.floor(number * multiplier) / multiplier;
    }

    public double generateDouble() {
        Random random = new Random();
        return minNumber + (maxNumber - minNumber) * random.nextDouble();
    }

    public static int generatePositiveInt() {
        Random random = new Random();
        return random.nextInt(0, Integer.MAX_VALUE);
    }
    @Override
    public double getMinNumber() {
        return minNumber;
    }
    @Override
    public double getMaxNumber() {
        return maxNumber;
    }
}
