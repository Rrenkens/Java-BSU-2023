package by.Katya841.quizer.tasks;

public interface MathTask extends Task {
    static final double eps = 0.00000001;
    interface Generator extends Task.Generator {
        int getMinNumber();
        int getMaxNumber();

        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
