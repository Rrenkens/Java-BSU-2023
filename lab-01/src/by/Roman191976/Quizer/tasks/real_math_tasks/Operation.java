package by.Roman191976.Quizer.tasks.real_math_tasks;

public enum Operation {
    SUM("+"),
    DIFFERENCE("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}