package by.rycbaryana.quizer.tasks.math_tasks;

public enum Operation {
    SUM,
    SUB,
    MULT,
    DIV;

    @Override
    public String toString() {
        return switch (this) {
            case SUM -> "+";
            case SUB -> "-";
            case MULT -> "*";
            case DIV -> "/";
        };
    }
}
