package by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.CantDoGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.MinGreaterThanMax;
import java.util.Random;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    private final int minNumber;
    private final int maxNumber;
    private final boolean generateSum_;
    private final boolean generateDifference_;
    private final boolean generateMultiplication_;
    private final boolean generateDivision_;
    private int first;
    private int second;
    private String operation;

    public AbstractMathTaskGenerator (
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        if(!generateDifference && !generateDivision && !generateMultiplication && !generateSum) {
            throw new CantDoGenerator();
        }
        if (minNumber > maxNumber) {
            throw new MinGreaterThanMax();
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        generateSum_ = generateSum;
        generateDifference_ = generateDifference;
        generateMultiplication_ = generateMultiplication;
        generateDivision_ = generateDivision;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    public int madeRandomNumb() {
        Random random = new Random();
        int numb = random.nextInt(getDiffNumber() + 1);
        numb += getMinNumber();
        return numb;
    }

    public void chooseOperation() {
        first = madeRandomNumb();
        second = madeRandomNumb();

        Random random = new Random();
        int numbOfOperation = random.nextInt(4);

        StringBuilder operation = new StringBuilder();
        boolean flag = true;
        while (flag) {
            switch (numbOfOperation) {
                case 0:
                    if (!generateSum_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('+');
                    flag = false;
                    break;
                case 1:
                    if (!generateDifference_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('-');
                    flag = false;
                    break;
                case 2:
                    if (!generateMultiplication_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('*');
                    flag = false;
                    break;
                case 3:
                    if (!generateDivision_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('/');
                    flag = false;
                    break;
                default:
                    numbOfOperation = random.nextInt(4);
                    break;
            }
        }
        this.operation = operation.toString();
    }

    public int getFirst(){
        return first;
    }

    public int getSecond(){
        return second;
    }

    public String getOperation(){
        return operation;
    }
}
