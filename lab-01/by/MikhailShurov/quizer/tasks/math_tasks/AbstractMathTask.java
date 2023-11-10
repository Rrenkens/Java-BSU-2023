package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask.Operation;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.*;

abstract class AbstractMathTask implements Task {
    String text;
    String answer;

    protected static boolean isNumeric(String str) {
        return str.matches("-?\\d+(,\\d+)?");
    }
    public static abstract class Generator implements MathTask.Generator {
        ArrayList<Character> operationsList = new ArrayList<>();
        double minNumber;
        double maxNumber;
        int precision;

        public int getPrecision() {
            return precision;
        }

        protected Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            this.precision = precision;

            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("Value error: maxNumber < minNumber");
            }

            if (operations.contains(Operation.Sum)) {
                operationsList.add('+');
            }
            if (operations.contains(Operation.Difference)) {
                operationsList.add('-');
            }
            if (operations.contains(Operation.Multiplication)) {
                operationsList.add('*');
            }
            if (operations.contains(Operation.Division)) {
                operationsList.add('/');
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
        }
    }

    public AbstractMathTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(String answer) {
        if (this.answer.equals(answer)) {
            return Result.OK;
        }

        String task = this.text;
        boolean checkAnswerByCalculating = true;
        Map<String, Integer> operations = new HashMap<>() {{
            put("/", 1);
            put("*", 1);
            put("+", 1);
            put("-", 1);
        }};

        if (task.contains("x")) {
            checkAnswerByCalculating = false;
            task = task.replace("x", String.valueOf(answer));
        } else {
            task = task.replace("?", String.valueOf(answer));
        }

        StringBuilder firstNum = new StringBuilder();
        StringBuilder secondNum = new StringBuilder();
        StringBuilder thirdNum = new StringBuilder();

        boolean isSecond = false;
        boolean isThird = false;
        for (int i = 0; i < task.length(); ++i) {
            if (task.charAt(i) == '=') {
                isThird = true;
                continue;
            }
            if (operations.get(String.valueOf(task.charAt(i))) != null && i != 0 && !isSecond) {
                isSecond = true;
            } else if (!isSecond && !isThird) {
                firstNum.append(task.charAt(i));
            } else if (isSecond && !isThird) {
                secondNum.append(task.charAt(i));
            } else if (isThird) {
                thirdNum.append(task.charAt(i));
            }
        }

        try {
            double solution = checkAnswerByCalculating(Double.parseDouble(firstNum.toString()), Double.parseDouble(secondNum.toString()), task);
            if (solution == Double.parseDouble(thirdNum.toString())) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (Exception e) {
            return Result.INCORRECT_INPUT;
        }
    }

    private double checkAnswerByCalculating(double firstnum, double secondNum, String task) {
        if (task.contains("*")) {
            return firstnum * secondNum;
        } else if (task.contains("/")) {
            return firstnum / secondNum;
        } else if (task.contains("+")) {
            return firstnum + secondNum;
        } else {
            return firstnum - secondNum;
        }
    }
}
