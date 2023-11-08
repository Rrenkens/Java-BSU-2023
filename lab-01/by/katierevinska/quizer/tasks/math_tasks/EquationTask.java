package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;


import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {

//```
//
//        ### EquationTaskGenerator
//        ���������� ��������� ���� `<num1><operator>x=<answer>` � `x<operator><num2>=<answer>`. ��������, `x/2=6`.
//
//        ```java

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              ����������� �����
         * @param maxNumber              ������������ �����
         * @param generateSum            ��������� ��������� � ���������� +
         * @param generateDifference     ��������� ��������� � ���������� -
         * @param generateMultiplication ��������� ��������� � ���������� *
         * @param generateDivision       ��������� ��������� � ���������� /
         */
        private final double minNumber;
        private final double maxNumber;
        private final int precision;

        private MathTask.Operation[] allowedOperations;

        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            this(minNumber, maxNumber,0, operations);
        }
        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            if(minNumber> maxNumber){
                throw new IllegalArgumentException("minNumber shouldn't be more than maxNumber");
            }else  if(precision< 0){
                throw new IllegalArgumentException("precision shouldn't be negative");
            }else {
                this.precision = precision;
                this.minNumber = minNumber;
                this.maxNumber = maxNumber;

                int numberOfAllowedOperations = 0;
                if (operations.contains(MathTask.Operation.Sum)) {
                    numberOfAllowedOperations++;
                }
                if (operations.contains(MathTask.Operation.Difference)) {
                    numberOfAllowedOperations++;
                }
                if (operations.contains(MathTask.Operation.Multiplication)) {
                    numberOfAllowedOperations++;
                }
                if (operations.contains(MathTask.Operation.Division)) {
                    numberOfAllowedOperations++;
                }
                allowedOperations = new MathTask.Operation[numberOfAllowedOperations];
                int pos = 0;
                if (operations.contains(MathTask.Operation.Sum)) {
                    allowedOperations[pos++] = MathTask.Operation.Sum;
                }
                if (operations.contains(MathTask.Operation.Difference)) {
                    allowedOperations[pos++] = MathTask.Operation.Difference;
                }
                if (operations.contains(MathTask.Operation.Multiplication)) {
                    allowedOperations[pos++] = MathTask.Operation.Multiplication;
                }
                if (operations.contains(MathTask.Operation.Division)) {
                    allowedOperations[pos] = MathTask.Operation.Division;
                }
            }
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
         * return ������� ���� {@link EquationTask}
         */
        public EquationTask generate()  {
            if(allowedOperations.length==0){
                throw new NoOperationsAllowedException( "should be allowed operations ");
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);//TODO can be fasterx
            StringBuilder expression = new StringBuilder();
            double answer=0;
            String num1 = generatingDoubleWithPrecision( minNumber, maxNumber, precision);
            String num2 = generatingDoubleWithPrecision( minNumber, maxNumber, precision);
            double viewOfEquation = ThreadLocalRandom.current().nextDouble(0, 2);
            if(viewOfEquation == 0) {
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append("x+")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = Double.parseDouble(num2) - Double.parseDouble(num1);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append("x-")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = Double.parseDouble(num2) + Double.parseDouble(num1);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if(Objects.equals(num1, "0")){
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append("x*")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = Double.parseDouble(num2) / Double.parseDouble(num1);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if(Objects.equals(num1, "0")){
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append("x/")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = Double.parseDouble(num1) * Double.parseDouble(num2);
                }
            }else{
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append(num1)
                            .append("+x=")
                            .append(num2);
                    answer = Double.parseDouble(num2) - Double.parseDouble(num1);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append(num1)
                            .append("-x=")
                            .append(num2);
                    answer = Double.parseDouble(num1) - Double.parseDouble(num2);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if(Objects.equals(num1, "0")){
                        num1=generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append(num1)
                            .append("*x=")
                            .append(num2);
                    answer = Double.parseDouble(num2) / Double.parseDouble(num1);
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if(Objects.equals(num1, "0")){
                        num1=generationWithout0(minNumber, maxNumber, precision);
                    }
                    if(Objects.equals(num1, "0")){
                        num2=generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append(num1)
                            .append("/x=")
                            .append(num2);
                    answer = Double.parseDouble(num1) / Double.parseDouble(num2);
                }
            }
            return new EquationTask(expression.toString(), String.format("%."+precision +"f", num1));
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