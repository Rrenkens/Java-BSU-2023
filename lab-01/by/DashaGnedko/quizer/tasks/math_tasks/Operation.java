package by.DashaGnedko.quizer.tasks.math_tasks;

public enum Operation {
    SUM,
    DIFFERENCE,
    MULTIPLICATION,
    DIVISION;

    public String toString() {
        switch (this) {
            case Operation.SUM -> {
                return "+";
            }
            case Operation.DIFFERENCE -> {
                return "-";
            }
            case Operation.MULTIPLICATION -> {
                return "*";
            }
            default -> {
                return "/";
            }
        }
    }
}
