package by.lokdestro.quizer.tasks.math_tasks;

public class ExpressionMathTask extends AbstractMathTask {

    public ExpressionMathTask(String answer, String taskName) {
        super(answer, taskName);
    }

    @Override
    public String getAns() {
        return ans;
    }
}