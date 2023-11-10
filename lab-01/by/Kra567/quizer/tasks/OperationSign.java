package by.Kra567.quizer.tasks;

public class OperationSign {
    public static String sign(OperationType operationType) {
        return switch (operationType) {
            case OperationType.DIFF -> "-";
            case OperationType.SUM -> "+";
            case OperationType.DIV -> "/";
            case OperationType.MUL -> "+";
        };
    }
}
