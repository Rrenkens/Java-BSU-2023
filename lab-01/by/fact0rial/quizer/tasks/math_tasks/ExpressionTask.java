package by.fact0rial.quizer.tasks.math_tasks;

import by.fact0rial.quizer.Task;
import by.fact0rial.quizer.tasks.math_tasks.AbstractMathTask;

import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Random;
import java.math.BigDecimal;

public class ExpressionTask extends AbstractMathTask{
    ExpressionTask(String text, double answer) {
        super(text, answer);
    }
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }
        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations, int prec) {
            super(minNumber, maxNumber, operations, prec);
        }
        @Override
        public Task generate() {
            String txt = "";
            double ans = 0;
            Random rand = new Random();
            int num = rand.nextInt(super.operations.size());
            Operation op = (Operation) super.operations.toArray()[num];
            double val1 = rand.nextDouble();
            double val2 = rand.nextDouble();
            val1 = super.minNumber * (1 - val1) + super.maxNumber * (1 + val1);
            val2 = super.minNumber * (1 - val2) + super.maxNumber * (1 + val2);
            switch(op) {
                case SUM -> {
                    val1 = new BigDecimal(val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    val2 = new BigDecimal(val2).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    txt = val1 + " + " + val2;
                    ans = new BigDecimal(val2 + val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                }
                case DIFFERENCE -> {
                    val1 = new BigDecimal(val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    val2 = new BigDecimal(val2).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    txt = val1 + " - " + val2;
                    ans = new BigDecimal(val1 - val2).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                }
                case DIVISION -> {
                    while (val1 == 0) {
                        val1 = rand.nextDouble();
                        val1 = new BigDecimal(super.minNumber * (1 - val1) + super.maxNumber * (val1)).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    }
                    val1 = new BigDecimal(val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    val2 = new BigDecimal(val2).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    txt = val2 + " / " + val1;
                    ans = new BigDecimal(val2 / val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                }
                case MULTIPLICATION -> {
                    val1 = new BigDecimal(val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    val2 = new BigDecimal(val2).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                    txt = val1 + " * " + val2;
                    ans = new BigDecimal(val2 * val1).setScale(numbers, RoundingMode.HALF_UP).doubleValue();
                }
            }
            return new EquationTask(txt, ans);
        }
    }
}
