package by.fact0rial.quizer.tasks;

import by.fact0rial.quizer.Task;
import by.fact0rial.quizer.tasks.AbstractMathTask;
import java.util.Random;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    EquationTask(String text, double answer) {
        super(text, answer);
    }
    static public class Generator extends AbstractMathTask.Generator {
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
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
                    txt = val1 + "+ x = " + val2;
                    ans = val2 - val1;
                }
                case DIFFERENCE -> {
                    txt = val1 + " - x = " + val2;
                    ans = val1 - val2;
                }
                case DIVISION -> {
                    while (val1 == 0) {
                        val1 = rand.nextDouble();
                        val1 = super.minNumber * (1 - val1) + super.maxNumber * (1 + val1);
                    }
                    txt = "x / " + val1 + " = " + val2;
                    ans = val2 * val1;
                }
                case MULTIPLICATION -> {
                    while (val1 == 0) {
                        val1 = rand.nextDouble();
                        val1 = super.minNumber * (1 - val1) + super.maxNumber * (1 + val1);
                    }
                    txt = val1 + " * x = " + val2;
                    ans = val2 / val1;
                }
            }
            return new EquationTask(txt, ans);
        }
    }
}
