package by.lamposhka.quizer.tasks.math_tasks;

import java.util.EnumSet;

public class AppleTask extends AbstractMathTask {
    AppleTask(String text, int answer) {
        super(text, answer, 0);
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final String name;

        public Generator(String name) {
            super(0, 10, 0, EnumSet.of(MathTask.Operation.DIFFERENCE));
            this.name = name;
        }

        @Override
        public AppleTask generate() {
            int num1 = (int) super.generateNum();
            int num2 = (int) super.generateNum();
            while (num1 < num2) {
                num2 = (int) super.generateNum();
            }
            String text = String.format("У %s было %d яблок, он(она) подарил(а) котику %d яблок. Сколько яблок осталось у %s?", name, num1, num2, name);
            return new AppleTask(text, num1 - num2);
        }
    }
}
