package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

public interface MathTask extends Task {

    interface Generator extends Task.Generator {
        double getMinNumber();

        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

        @Override
        Task generate();
    }

    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION;

        @Override
        public String toString() {
            return switch (this) {
                case SUM -> "+";
                case DIFFERENCE -> "-";
                case MULTIPLICATION -> "*";
                case DIVISION -> "/";
            };
        }
    }

    @Override
    String getText();

    @Override
    Result validate(String answer);
}
