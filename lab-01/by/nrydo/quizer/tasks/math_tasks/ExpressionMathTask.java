package by.nrydo.quizer.tasks.math_tasks;

import by.nrydo.quizer.Result;

public class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(int x, int y, Operation operation) {
        if ((y == 0 || x % y != 0) && operation == Operation.Division) {
            throw new IllegalArgumentException();
        }

        String operation_symbol = switch (operation) {
            case SUM -> "+";
            case Difference -> "-";
            case Multiplication -> "*";
            case Division -> "/";
        };
        this.text =  x + operation_symbol + y + "=?";

        this.answer = Integer.toString(switch (operation) {
            case SUM -> x + y;
            case Difference -> x - y;
            case Multiplication -> x * y;
            case Division -> x / y;
        });
    }
}
