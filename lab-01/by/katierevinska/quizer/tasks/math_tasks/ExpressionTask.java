package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;



import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.round;

public class ExpressionTask extends AbstractMathTask {
    //���������� ������� ���� `<num1><operator><num2>=<answer>`. ��������, `2*5=?`.

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              ����������� �����
         * @param maxNumber              ������������ �����
         * @param generateSum            ��������� ��������� � ���������� +
         * @param generateDifference     ��������� ��������� � ���������� -
         * @param generateMultiplication ��������� ��������� � ���������� *
         * @param generateDivision       ��������� ��������� � ���������� /
         */
        private final int minNumber;
        private final int maxNumber;

        MathTask.Operation[] allowedOperations;

        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
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

        /**
         * return ������� ���� {@link ExpressionTask}
         */

        public ExpressionTask generate() throws Exception {
            if(allowedOperations.length==0){
                throw new Exception("no-one operation is allowed");
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);//TODO can be faster?
            StringBuilder expression = new StringBuilder();
            int answer=0;
            int num1 = ThreadLocalRandom.current().nextInt(minNumber, maxNumber+1);
            int num2 = ThreadLocalRandom.current().nextInt(minNumber, maxNumber+1);
            if(allowedOperations[randomNum] == MathTask.Operation.Sum){
                expression.append(num1)
                        .append('+')
                        .append(num2<0?"("+num2+")":num2)
                        .append("=?");
                answer = num1+num2;
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Difference){
                expression.append(num1)
                        .append('-')
                        .append(num2<0?"("+num2+")":num2)
                        .append("=?");
                answer =num1-num2;
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Multiplication){
                expression.append(num1)
                        .append('*')
                        .append(num2<0?"("+num2+")":num2)
                        .append("=?");
                answer = num1*num2;
            }
            else if(allowedOperations[randomNum] == MathTask.Operation.Division){
                if(num2 == 0){
                    num2 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
                }
                expression.append(num1)
                        .append('/')
                        .append(num2<0?"("+num2+")":num2)
                        .append("=?");
                answer = num1/num2;
            }
            return new ExpressionTask(expression.toString(), String.valueOf(answer));
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

