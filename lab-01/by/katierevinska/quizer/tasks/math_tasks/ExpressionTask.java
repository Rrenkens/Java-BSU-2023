package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;


import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class ExpressionTask extends AbstractMathTask {
    //Генерирует примеры вида `<num1><operator><num2>=<answer>`. Например, `2*5=?`.

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateSum            разрешить генерацию с оператором +
         * @param generateDifference     разрешить генерацию с оператором -
         * @param generateMultiplication разрешить генерацию с оператором *
         * @param generateDivision       разрешить генерацию с оператором /
         */
        private final double minNumber;
        private final double maxNumber;
        private final int precision;
        
        private MathTask.Operation[] allowedOperations;

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations

        ) {
            this.precision = precision;
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

        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
                ) {
            this(minNumber, maxNumber, 0, operations);
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        /**
         * return задание типа {@link ExpressionTask}
         */

        public ExpressionTask generate() {
            if(allowedOperations.length==0){
                throw new NoOperationsAllowedException( "should be allowed operations ");
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);//TODO can be faster?
            StringBuilder expression = new StringBuilder();
            double answer=0;
            String num1 = generatingDoubleWithPrecision( minNumber, maxNumber, precision);
            String num2 = generatingDoubleWithPrecision( minNumber, maxNumber, precision);


            if(allowedOperations[randomNum] == Operation.Sum){
                expression.append(num1)
                        .append('+')
                        .append(formationWithBracket(num2))
                        .append("=?");
                answer = Double.parseDouble(num1)+Double.parseDouble(num2);
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Difference){
                expression.append(num1)
                        .append('-')
                        .append(formationWithBracket(num2))
                        .append("=?");
                answer =Double.parseDouble(num1)-Double.parseDouble(num2);
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Multiplication){
                expression.append(num1)
                        .append('*')
                        .append(formationWithBracket(num2))
                        .append("=?");
                answer = Double.parseDouble(num1)*Double.parseDouble(num2);;
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Division){
                if(Objects.equals(num2, "0")){
                    num2 = generationWithout0( minNumber, maxNumber, precision);
                }
                expression.append(num1)
                        .append('/')
                        .append(formationWithBracket(num2))
                        .append("=?");
                answer = Double.parseDouble(num1)/Double.parseDouble(num2);
            }
            return new ExpressionTask(expression.toString(), String.format("%."+precision +"f", num1));
        }
    }
    public ExpressionTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }
}

