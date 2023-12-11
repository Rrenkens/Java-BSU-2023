package by.lokdestro.quizer.tasks.math_tasks;

public class EquationMathTask extends AbstractMathTask {

    public EquationMathTask(String answer, String taskName) {
        super(answer, taskName);
    }

    @Override
    public String getAns() {
        return ans;
    }
}