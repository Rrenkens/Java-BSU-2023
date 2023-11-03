package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;


import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {

//```
//
//        ### EquationTaskGenerator
//        Генерирует уравнения вида `<num1><operator>x=<answer>` и `x<operator><num2>=<answer>`. Например, `x/2=6`.
//
//        ```java

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateSum            разрешить генерацию с оператором +
         * @param generateDifference     разрешить генерацию с оператором -
         * @param generateMultiplication разрешить генерацию с оператором *
         * @param generateDivision       разрешить генерацию с оператором /
         */
        private final int minNumber;
        private final int maxNumber;

        MathTask.Operation[] allowedOperations;

        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            {
                this.minNumber = minNumber;
                this.maxNumber = maxNumber;

                int numberOfAllowedOperations = 0;
                if(operations.contains(MathTask.Operation.Sum)){
                    numberOfAllowedOperations++;
                }
                if(operations.contains(MathTask.Operation.Difference)){
                    numberOfAllowedOperations++;
                }
                if(operations.contains(MathTask.Operation.Multiplication)){
                    numberOfAllowedOperations++;
                }
                if(operations.contains(MathTask.Operation.Division)){
                    numberOfAllowedOperations++;
                }
                allowedOperations = new MathTask.Operation[numberOfAllowedOperations];
                int pos = 0;
                if(operations.contains(MathTask.Operation.Sum)){
                    allowedOperations[pos++]= MathTask.Operation.Sum;
                }
                if(operations.contains(MathTask.Operation.Difference)){
                    allowedOperations[pos++]= MathTask.Operation.Difference;
                }
                if(operations.contains(MathTask.Operation.Multiplication)){
                    allowedOperations[pos++]= MathTask.Operation.Multiplication;
                }
                if(operations.contains(MathTask.Operation.Division)){
                    allowedOperations[pos]= MathTask.Operation.Division;
                }

            }
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() throws Exception {
            if(allowedOperations.length==0){
                throw new Exception("no-one operation is allowed");
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);//TODO can be fasterx
            StringBuilder expression = new StringBuilder();
            int answer=0;
            int num1 = ThreadLocalRandom.current().nextInt(minNumber, maxNumber+1);
            int num2 = ThreadLocalRandom.current().nextInt(minNumber, maxNumber+1);
            int viewOfEquation = ThreadLocalRandom.current().nextInt(0, 2);
            if(viewOfEquation == 0) {
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append("x+")
                            .append(num1<0?"("+num1+")":num1)
                            .append("=")
                            .append(num2);
                    answer = num2 - num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append("x-")
                            .append(num1<0?"("+num1+")":num1)
                            .append("=")
                            .append(num2);
                    answer = num1 + num2;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if(num1 == 0){
                        if(maxNumber>0){
                            num1 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                        }else{
                            num1 = ThreadLocalRandom.current().nextInt(minNumber, -2);
                        }
                    }
                    expression.append("x*")
                            .append(num1<0?"("+num1+")":num1)
                            .append("=")
                            .append(num2);
                    answer = num2 / num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if(num1 == 0){
                        if(maxNumber>0){
                            num1 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                        }else{
                            num1 = ThreadLocalRandom.current().nextInt(minNumber, -2);
                        }
                    }
                    expression.append("x/")
                            .append(num1<0?"("+num1+")":num1)
                            .append("=")
                            .append(num2);
                    answer = num1 * num2;
                }
            }else{
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append(num1)
                            .append("+x=")
                            .append(num2);
                    answer = num2 - num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append(num1)
                            .append("-x=")
                            .append(num2);
                    answer = num1 - num2;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if(num1 == 0){
                        if(maxNumber>0){
                            num1 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                        }else{
                            num1 = ThreadLocalRandom.current().nextInt(minNumber, -2);
                        }
                    }
                    expression.append(num1)
                            .append("*x=")
                            .append(num2);
                    answer = num2 / num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if(num1 == 0){
                        if(maxNumber>0){
                            num1 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                        }else{
                            num1 = ThreadLocalRandom.current().nextInt(minNumber, -2);
                        }
                    }
                    if(num2 == 0){
                        if(maxNumber>0){
                            num2 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                        }else{
                            num2 = ThreadLocalRandom.current().nextInt(minNumber, -2);
                        }
                    }
                    expression.append(num1)
                            .append("/x=")
                            .append(num2);
                    answer = num1 / num2;
                }
            }
            return new EquationTask(expression.toString(), String.valueOf(answer));
        }
    }
    public EquationTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

}