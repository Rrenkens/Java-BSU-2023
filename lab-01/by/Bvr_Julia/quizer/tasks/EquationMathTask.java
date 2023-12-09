package by.Bvr_Julia.quizer.tasks;

import by.Bvr_Julia.quizer.Randomizer;
import by.Bvr_Julia.quizer.Result;
import by.Bvr_Julia.quizer.exeptions.QuizAllNumbersForbiddenException;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    private int answer;
    private final int num1;
    private final int num2;
    private final boolean generateSum;
    private final boolean generateDifference;
    private final boolean generateMultiplication;
    private final boolean generateDivision;
    private final int xParam;

    public EquationMathTask(int num1, int num2, boolean generateSum, boolean generateDifference,
                            boolean generateMultiplication, boolean generateDivision, int xParam) {
        this.num1 = num1;
        this.num2 = num2;
        this.generateMultiplication = generateMultiplication;
        this.generateDifference = generateDifference;
        this.generateDivision = generateDivision;
        this.generateSum = generateSum;
        this.xParam=xParam;
        if (generateSum) {
            answer = num2 - num1;
        } else if (generateDifference && (xParam == 1)) {
            answer = num1 + num2;
        } else if (generateDivision && (xParam == 1)) {
            answer = num1 * num2;
        }else if (generateDifference && (xParam == 2)) {
            answer = num1 - num2;
        } else if (generateDivision && (xParam == 2)) {
            answer = num1 / num2;
        } else if (generateMultiplication) {
            answer = num2 / num1;
        }
    }
    @Override
    public String getText(){
        StringBuilder res = new StringBuilder();
        if (xParam == 1){
            res.append('x');
        }else{
            res.append(String.valueOf(num1));
        }
        if (generateSum){
            res.append('+');
        } else if (generateDifference) {
            res.append('-');
        } else if (generateDivision) {
            res.append('/');
        } else if (generateMultiplication) {
            res.append('*');
        }
        if (xParam == 2){
            res.append('x');
        }else{
            res.append(String.valueOf(num1));
        }
        res.append('=');
        res.append(String.valueOf(num2));
        return  res.toString();
    }

    @Override
    public Result validate(String answer){
        super.answer = this.answer;
        return super.validate(answer);
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final int minNumber;
        private final int maxNumber;
        EnumSet<Operation> operations;

        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            if (operations.isEmpty()) {
                throw new QuizAllNumbersForbiddenException();
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = EnumSet.copyOf(operations);
        }
        @Override
        public EquationMathTask generate() {
            int num1 = Randomizer.generate(minNumber, maxNumber);
            int num2 = Randomizer.generate(minNumber, maxNumber);
            int xParam = Randomizer.generate(1, 2);
            int numOperations = operations.size();
            int operation = Randomizer.generate(1, numOperations);
            boolean sum = false;
            boolean difference = false;
            boolean multiplication = false;
            boolean division = false;
            int tmp = 1;
            if (operations.contains(Operation.Sum)) {
                if (operation == tmp) {
                    sum = true;
                    tmp = 100;
                } else {
                    tmp++;
                }
            }
            if (operations.contains(Operation.Difference)) {
                if (operation == tmp) {
                    difference = true;
                    tmp = 100;
                } else {
                    tmp++;
                }
            }
            if (operations.contains(Operation.Multiplication)) {
                if (operation == tmp) {
                    multiplication = true;
                    num2 *= num1;
                    tmp = 100;
                } else {
                    tmp++;
                }
            }
            if (operations.contains(Operation.Division)) {
                if (operation == tmp) {
                    division = true;
                    tmp = 100;
                    if (xParam == 2) {
                        num1 *= num2;
                    }
                }
            }
            while (division && (num1 == 0 || num2 == 0)) {
                if (minNumber == maxNumber && maxNumber == 0) {
                    throw new QuizAllNumbersForbiddenException();
                }
                num1 = Randomizer.generate(minNumber, maxNumber);
                num2 = Randomizer.generate(minNumber, maxNumber);
                if (xParam == 2) {
                    num1 *= num2;
                }
            }
            while (multiplication && (num1 == 0 || num2 == 0)) {
                if (minNumber == maxNumber && maxNumber == 0) {
                    throw new QuizAllNumbersForbiddenException();
                }
                num1 = Randomizer.generate(minNumber, maxNumber);
                num2 = Randomizer.generate(minNumber, maxNumber);
                num2 *= num1;
            }
            return new EquationMathTask(num1, num2, sum, difference, multiplication, division, xParam);
        }

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }
    }
}

