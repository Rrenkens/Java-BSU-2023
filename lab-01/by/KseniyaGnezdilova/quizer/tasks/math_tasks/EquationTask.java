package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

import by.KseniyaGnezdilova.quizer.Result;

import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class EquationTask extends AbstractMathTask {
    private int precision;
    private boolean unknown;
    private double firstNum;
    private double secondNum;

    private double thirdNum;
    private String operation;
    private double ans;

    public EquationTask(boolean unknown, double firstNum, double secondNum, double thirdNum, String operation, int precision) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.thirdNum = thirdNum;
        this.operation = operation;
        this.ans = 0;
        this.unknown = unknown;
        this.precision = precision;
    }

    public EquationTask() {
        this.firstNum = 0;
        this.secondNum = 0;
        this.thirdNum = 0;
        this.operation = null;
        this.ans = 0;
        this.unknown = false;
        this.precision = 0;
    }

    @Override
    public String getText() {
        return (this.unknown ? "X " + this.operation + " " + this.secondNum + " = " + this.thirdNum :
                                this.firstNum + " " + this.operation + " X = " + this.thirdNum );
    }

    @Override
    public Result validate(String answer) {
        switch (this.operation){
            case "+" -> {
                if (this.unknown) {
                    this.ans = this.thirdNum - this.secondNum;
                } else {
                    this.ans = this.thirdNum - this.firstNum;
                }
            }
            case "-" -> {
                if (this.unknown){
                    this.ans = this.secondNum + this.thirdNum;
                } else {
                    this.ans = this.firstNum - this.thirdNum;
                }
            }
            case "*" -> {
                if (this.unknown) {
                    this.ans = this.thirdNum / this.secondNum;
                } else {
                    this.ans = this.thirdNum / this.firstNum;
                }
            }

            case "/" -> {
                if (this.unknown) {
                    this.ans = this.thirdNum * this.secondNum;
                } else {
                    this.ans = this.firstNum / this.thirdNum;
                }
            }
        }
        return (Math.abs(Double.parseDouble(answer) - this.ans) < Math.pow(0.1, 2 * precision)? Result.OK : Result.WRONG);
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

        public EquationTask generate() {
            Random random = new Random();
            double firstNum = random.nextDouble( getDiffNumber() + 1) + this.minNumber;
            double secondNum = random.nextDouble(getDiffNumber() + 1) + this.minNumber;
            firstNum = Math.round(firstNum * Math.pow(10, precision)) / Math.pow(10, precision);
            secondNum = Math.round(secondNum * Math.pow(10, precision)) / Math.pow(10, precision);
            double thirdNum = 0;
            Vector<String> operations = new Vector<String>();
            if (this.generateSum) operations.add("+");
            if (this.generateDifference) operations.add("-");
            if (this.generateMultiplication) operations.add("*");
            if (this.generateDivision) operations.add("/");
            int pos = random.nextInt(operations.size());
            EquationTask equationTask = new EquationTask();
            boolean unknown = random.nextBoolean();
            if (!unknown && secondNum == 0 && Objects.equals(operations.get(pos), "/")) {
                generate();
            } else {
                switch (operations.get(pos)) {
                    case "+" -> thirdNum = firstNum + secondNum;
                    case "-" -> thirdNum = firstNum - secondNum;
                    case "*" -> thirdNum = firstNum * secondNum;
                    case "/" -> thirdNum = firstNum / secondNum;
                }
            }
            thirdNum = Math.round(firstNum * Math.pow(10, 2 * precision)) / Math.pow(10, 2 * precision);
            equationTask = new EquationTask(unknown, firstNum, secondNum, thirdNum, operations.get(pos), precision);
            return  equationTask;
        }
    }
}