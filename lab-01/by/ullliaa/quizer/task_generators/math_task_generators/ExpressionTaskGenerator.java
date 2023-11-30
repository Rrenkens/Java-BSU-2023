package by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.Objects;

public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
    public ExpressionTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    public String madeText() {
        return String.valueOf(getFirst()) + getOperation() + String.valueOf(getSecond()) + "=?";
    }

    public double getAnswer() {
        double answer = 0;
        if (Objects.equals(getOperation(), "+")) {
            answer = getFirst() + getSecond();
        } else if (Objects.equals(getOperation(), "-")) {
            answer = getFirst() - getSecond();
        } else if (Objects.equals(getOperation(), "*")) {
            answer = getFirst() * getSecond();
        } else if (Objects.equals(getOperation(), "/")) {
            answer = ((double) getFirst()) / getSecond();
        }
        return answer;
    }
    /**
     * return задание типа {@link ExpressionMathTask}
     */
    @Override
    public ExpressionMathTask generate() {
        chooseOperation();
        while (Objects.equals(getOperation(), "/") && getSecond() == 0) {
            chooseOperation();
        }
        return new ExpressionMathTask(madeText(), getAnswer());
    }
}
