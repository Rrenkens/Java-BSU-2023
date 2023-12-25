package by.Kra567.quizer.tasks;

import by.Kra567.quizer.basics.Result;
import by.Kra567.quizer.basics.Task;

public class EquationTask  implements Task {
    private OperationType operationType;
    private int firstArgument;
    private int secondArgument;
    private boolean isLeft;

    public EquationTask(OperationType operationType,boolean isLeft,int firstArgument,int secondArgument){
        this.operationType = operationType;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.isLeft = isLeft;
    }

    @Override
    public String getText() {
        if (isLeft){
            return "x "+OperationSign.sign(operationType)+ " " +
                    Integer.valueOf(firstArgument).toString()+" = "+
                    Integer.valueOf(secondArgument).toString()+", x - ?";
        }
        return Integer.valueOf(firstArgument).toString()+
                " " + OperationSign.sign(operationType)+
                " x = " + Integer.valueOf(secondArgument).toString() + ", x - ?";
    }

    @Override
    public Result validate(String answer) {
        try {
            int val = Integer.parseInt(answer);
            boolean ok = switch (operationType){
                case OperationType.SUM -> (val + firstArgument) == secondArgument;
                case OperationType.MUL -> (val * firstArgument) == secondArgument;
                case OperationType.DIFF -> (isLeft ? 1 : -1) * (val - firstArgument) == secondArgument;
                case OperationType.DIV -> isLeft ? (val / firstArgument == secondArgument)
                                                 : (firstArgument / val == secondArgument);
            };
            return ok ? Result.OK : Result.WRONG;
        } catch (NumberFormatException e){
            return Result.INCORRECT_INPUT;
        }
    }
}
