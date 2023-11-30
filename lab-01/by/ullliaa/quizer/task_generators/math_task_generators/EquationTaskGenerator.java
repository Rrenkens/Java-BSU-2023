package by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks.EquationMathTask;

import java.util.Objects;
import java.util.Random;

public class EquationTaskGenerator extends AbstractMathTaskGenerator {
    protected int position;
    public EquationTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    public String madeText() {
        Random random = new Random();
        int position = random.nextInt(2);

        this.position = position;
        if (position == 1) {
            return 'x' + getOperation() + String.valueOf(getFirst()) + '=' + String.valueOf(getSecond());
        } else {
            return String.valueOf(getFirst()) + getOperation() + 'x' + '=' + String.valueOf(getSecond());
        }
    }

    public double getAnswer() {
        double answer = 0;
        if (position == 1) {
            if (Objects.equals(getOperation(), "+")) {
                answer = getSecond() - getFirst();
            } else if (Objects.equals(getOperation(), "-")) {
                answer = getSecond() + getFirst();
            } else if (Objects.equals(getOperation(), "*")) {
                answer = ((double) getSecond()) / getFirst();
            } else if (Objects.equals(getOperation(), "/")) {
                answer = getSecond() * getFirst();
            }
        } else {
            if (Objects.equals(getOperation(), "+")) {
                answer = getSecond() - getFirst();
            } else if (Objects.equals(getOperation(), "-")) {
                answer = getFirst() - getSecond();
            } else if (Objects.equals(getOperation(), "*")) {
                answer = ((double) getSecond()) / getFirst();
            } else if (Objects.equals(getOperation(), "/")) {
                answer = getFirst() / ((double) getSecond());
            }
        }
        return answer;
    }

    @Override
    public EquationMathTask generate() {
        chooseOperation();
        while (Objects.equals(getOperation(), "/") && getFirst() == 0) {
            chooseOperation();
        }
        while (Objects.equals(getOperation(), "*") && getFirst() == 0) {
            chooseOperation();
        }
        return new EquationMathTask(madeText(), getAnswer());
    }
}
