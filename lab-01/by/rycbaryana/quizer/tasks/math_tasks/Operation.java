package by.rycbaryana.quizer.tasks.math_tasks;

public enum Operation {
    SUM,
    SUB,
    MULT,
    DIV;

    @Override
    public String toString() {
        return switch (this) {
            case SUM -> {
                yield "+";
            }
            case SUB -> {
                yield "-";
            }
            case MULT -> {
                yield "*";
            }
            case DIV -> {
                yield "/";
            }
        };
    }
}
