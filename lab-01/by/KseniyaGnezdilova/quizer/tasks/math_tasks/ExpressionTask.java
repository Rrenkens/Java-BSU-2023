package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

import by.KseniyaGnezdilova.quizer.Result;

import javax.swing.*;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class ExpressionTask extends AbstractMathTask {

    private double firstNum;
    private double secondNum;
    private double ans;
    private String operation;

    private int precision;
    public ExpressionTask(){
        this.ans = 0;
        this.firstNum = 0;
        this.secondNum = 0;
        this.operation = null;
        this.precision = 1;
    }
    public ExpressionTask(double firstNum, double secondNum, String operation, int precision) {
        this.ans = 0;
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.operation = operation;
        this.precision = precision;
    }

    @Override
    public String getText() {
        return Double.toString(firstNum) + " " + operation + " " + Double.toString(secondNum) + " = ?";
    }

    @Override
    public Result validate(String answer) {
        switch (this.operation) {
            case "+" -> this.ans = this.firstNum + this.secondNum;
            case "-" -> this.ans = this.firstNum - this.secondNum;
            case "*" -> this.ans = this.firstNum * this.secondNum;
            case "/" -> this.ans = this.firstNum / this.secondNum;
        }
        return (Math.abs(Double.parseDouble(answer) - Math.round(this.ans * Math.pow(10, 2* this.precision)) / Math.pow(10, 2 * this.precision)) < Math.pow(0.1, 2 * this.precision) ? Result.OK : Result.WRONG);
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                int precision,
                double minNumber,
                double maxNumber,
                boolean generateSum,
                boolean generateDifference,
                boolean generateMultiplication,
                boolean generateDivision
        ) {
            super(  precision,
                    minNumber,
                    maxNumber,
                    generateSum,
                    generateDifference,
                    generateMultiplication,
                    generateDivision
            );
        }
        public ExpressionTask generate() {
            Random random = new Random();
            double firstNum = random.nextDouble(getDiffNumber()+ 1) + this.minNumber;
            double secondNum = random.nextDouble(getDiffNumber() + 1) + this.minNumber;
            firstNum = Math.round(firstNum * Math.pow(10, precision)) / Math.pow(10, precision);
            secondNum = Math.round(secondNum * Math.pow(10, precision)) / Math.pow(10, precision);
            Vector <String> operations = new Vector<String>();
            if (this.generateSum) operations.add("+");
            if (this.generateDifference) operations.add("-");
            if (this.generateMultiplication) operations.add("*");
            if (this.generateDivision) operations.add("/");
            int pos = random.nextInt(operations.size());
            ExpressionTask expressionTask = new ExpressionTask();
            if (secondNum == 0 && Objects.equals(operations.get(pos), "/")) {
                generate();
            } else {
                 expressionTask = new ExpressionTask(firstNum, secondNum, operations.get(pos), precision);
            }
            return expressionTask;
        }
    }
}