package by.Bvr_Julia.quizer.tasks;

import by.Bvr_Julia.quizer.task_generators.TaskGenerator;

public interface MathTask extends Task {
    enum Operation{
        Sum,
        Difference,
        Division,
        Multiplication
    }

    public static interface Generator extends TaskGenerator {
        public int getMinNumber();

        public int getMaxNumber();

        public default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}

