package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

import by.KseniyaGnezdilova.quizer.Result;

import javax.swing.*;
import java.util.EnumSet;
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
                EnumSet <Operations> operations
        ) {
            super(  precision,
                    minNumber,
                    maxNumber,
                    operations
            );
        }
        public ExpressionTask generate() {
            Random random = new Random();
            double firstNum = random.nextDouble(getDiffNumber()+ 1) + this.minNumber;
            double secondNum = random.nextDouble(getDiffNumber() + 1) + this.minNumber;
            firstNum = Math.round(firstNum * Math.pow(10, precision)) / Math.pow(10, precision);
            secondNum = Math.round(secondNum * Math.pow(10, precision)) / Math.pow(10, precision);
            Vector<String> operations_ = new Vector<>();
            for (var operation: operations){
                operations_.add(operation.name());
            }
            int pos = random.nextInt(operations_.size());
            ExpressionTask expressionTask = new ExpressionTask();
            String operator = operations_.get(pos);
            if (secondNum == 0 && Objects.equals(operator, "DIV")) {
                generate();
            } else {
                switch (operator) {
                    case "SUM" -> operator = "+";
                    case "DIFF" -> operator = "-";
                    case "MUL" -> operator = "*";
                    case "DIV" -> operator = "/";
                }
                expressionTask = new ExpressionTask(firstNum, secondNum, operator, precision);
            }
            return expressionTask;
        }
    }
}