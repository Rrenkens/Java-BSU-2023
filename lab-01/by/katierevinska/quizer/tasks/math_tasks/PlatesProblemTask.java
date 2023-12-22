package by.katierevinska.quizer.tasks.math_tasks;

import java.util.concurrent.ThreadLocalRandom;

public class PlatesProblemTask extends AbstractMathTask {
    public PlatesProblemTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    public static class Generator extends AbstractMathTask.Generator {

        private final int minNumber;
        private final int maxNumber;

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        public Generator(
                int minNumber,
                int maxNumber

        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
        }

        @Override
        public PlatesProblemTask generate() {
            if (minNumber <= 0) {
                throw new IllegalArgumentException("for this task minNumber should be more than 0");
            }
            int x = ThreadLocalRandom.current().nextInt(minNumber, maxNumber + 1);
            int y = ThreadLocalRandom.current().nextInt(minNumber, maxNumber + 1);
            return new PlatesProblemTask("Имеется " + x + " коробок с тарелками. В каждой по " + y + " тарелок. " + y + " тарелок разбилось при перевозке, сколько уцелело?)", String.valueOf(y * (x - 1)));
        }
    }
}
