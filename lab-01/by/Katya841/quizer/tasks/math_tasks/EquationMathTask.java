package by.Katya841.quizer.tasks.math_tasks;

import by.Katya841.quizer.Operation;
import by.Katya841.quizer.Rand;
import by.Katya841.quizer.Result;
import by.Katya841.quizer.exceptions.TaskGeneratingException;
import by.Katya841.quizer.tasks.AbstractMathTask;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    private int num1;
    private int num2;
    private Operation operation;
    private String znak;
    private int xId; // позиция x

    public EquationMathTask(int num1, int num2, Operation operation, int xId) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
        this.xId = xId;
        if (operation == Operation.Sum) {
            znak = "+";
        } else if (operation == Operation.Difference) {
            znak = "-";
        } else if (operation == Operation.Multiplication) {
            znak = "*";
        } else {
            znak = "/";
        }
        solveEquation();
    }

    private void solveEquation() {
        if (operation == Operation.Sum) {
            answer = num2 - num1;
        } else if (operation == Operation.Difference) {
            if (xId == 1) {
                answer = num2 + num1;
            } else {
                answer = num1 - num2;
            }
        } else if (operation == Operation.Division) {
            if (xId == 1) {
                answer = num1 * num2;

            } else {
               answer = (double)num1 / num2;
            }
        } else {
            answer = (double)num2 / num1;
        }

    }
    @Override
    public String getText() {
        StringBuilder text = new StringBuilder();
        String sum1, sum2, sum3;

        if (xId == 1) {
            sum1 = "x";
            sum2 = String.valueOf(num1);
            sum3 = String.valueOf(num2);
        } else {
            sum1 = String.valueOf(num1);
            sum2 = "x";
            sum3 = String.valueOf(num2);
        }
        text.append(sum1 + " ");
        text.append(znak + " ");
        text.append(sum2 + " ");
        text.append("= ");
        text.append(sum3);
        return text.toString();
    }
    @Override
    public Result validate(String answer) {
        return super.validate(answer);
    }

    public static class Generator extends AbstractMathTask.Generator {

        public Generator(int min, int max, EnumSet<Operation> operations){
            super(min, max, operations);
        }

        @Override
        public EquationMathTask generate() {
            int num1 = Rand.generateNumber(getMinNumber(), getMaxNumber());
            int num2 = Rand.generateNumber(getMinNumber(), getMaxNumber());
            int xId = Rand.generateNumber(1, 2);
            Operation operation = listOperations.get(Rand.generateNumber(0, listOperations.size() - 1));

            if (operation == Operation.Multiplication) {
                if (num1 == 0) {
                    num1++;
                } else {
                    /*if (num2 % num1 != 0) {
                        num2 *= num1;
                    }
                     */
                }
            }
            if (operation == Operation.Division) {
                if (xId == 2 && num2 == 0) {
                    num2++;
                } else if (xId == 1 && num1 == 0) {
                    num1++;
                }
                /*
                if (xId == 2 && num1 % num2 != 0) {
                    num1 *= num2;
                }
                 */
            }
            if (num1 > getMaxNumber()  || num2 > getMaxNumber()) {
                throw new TaskGeneratingException("TaskGeneratingException : Cannot creaate task with such num1 and num2");
            }

            return new EquationMathTask(num1, num2, operation, xId);
        }

    }

}
