package by.Katya841.quizer.tasks;

public interface MathTask extends Task {

    public  interface Generator extends Task.Generator {
        int getMinNumber();
        int getMaxNumber();

        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
