package by.Kra567.quizer.tasks;

import by.Kra567.quizer.basics.Result;
import by.Kra567.quizer.basics.Task;

public class ExpressionTask implements Task {
    private OperationType operationType;
    private int firstArgument;
    private int secondArgument;

    public ExpressionTask(OperationType operationType,int firstArgument,int secondArgument){
        this.operationType = operationType;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }


    @Override
    public Result validate(String answer) {
        try {
            int val = Integer.parseInt(answer);
            boolean ok = switch (operationType){
                case OperationType.SUM -> firstArgument + secondArgument == val;
                case OperationType.DIFF -> firstArgument - secondArgument == val;
                case OperationType.DIV -> firstArgument / secondArgument == val;
                case OperationType.MUL -> firstArgument * secondArgument == val;
            };
            return ok ? Result.OK : Result.WRONG;
        } catch (NumberFormatException e){
            return Result.INCORRECT_INPUT;
        }
    }

    @Override
    public String getText() {
        return Integer.valueOf(firstArgument).toString() + " " +
                OperationSign.sign(operationType) + " " +
                Integer.valueOf(secondArgument).toString() +
                " = ?";
    }
}
