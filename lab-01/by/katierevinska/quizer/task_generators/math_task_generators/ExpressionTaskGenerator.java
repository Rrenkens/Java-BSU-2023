package by.katierevinska.quizer.task_generators.math_task_generators;


import by.katierevinska.quizer.TaskGenerator;
import by.katierevinska.quizer.tasks.math_tasks.ExpressionTask;
import by.katierevinska.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

//Генерирует примеры вида `<num1><operator><num2>=<answer>`. Например, `2*5=?`.
public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
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

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> operations
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
     * return задание типа {@link ExpressionTask}
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