package by.Katya841.quizer.tasks;

import by.Katya841.quizer.Operation;
import by.Katya841.quizer.Result;


import java.util.*;

public abstract class AbstractMathTask implements MathTask {
    protected int answer;
    protected String text;

    public Result validate(String answer) {
        try {
            int number = Integer.parseInt(answer);
            if (this.answer == number) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    abstract public static class Generator implements MathTask.Generator {
        int min;
        int max;
        EnumSet<Operation> operations;
        public ArrayList<Operation> listOperations;
        public Generator(int min, int max, EnumSet<Operation> operations) {
            this.min = min;
            this.max = max;
            this.operations = operations;
            listOperations = new ArrayList<Operation>(operations);
        }
        public int getMinNumber() {
            return min;
        }
        public int getMaxNumber() {
            return max;
        }

    }
}
