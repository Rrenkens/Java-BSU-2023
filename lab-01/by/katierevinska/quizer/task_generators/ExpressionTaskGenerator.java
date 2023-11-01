package by.katierevinska.quizer.task_generators;


import by.katierevinska.quizer.TaskGenerator;
import by.katierevinska.quizer.tasks.ExpressionTask;

import java.util.concurrent.ThreadLocalRandom;

//Генерирует примеры вида `<num1><operator><num2>=<answer>`. Например, `2*5=?`.
public class ExpressionTaskGenerator implements TaskGenerator {
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

    Operation[] allowedOperations;

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        int numberOfAllowedOperations = 0;
        if(generateSum){
            numberOfAllowedOperations++;
        }
        if(generateDifference){
            numberOfAllowedOperations++;
        }
        if(generateMultiplication){
            numberOfAllowedOperations++;
        }
        if(generateDivision){
            numberOfAllowedOperations++;
        }
        allowedOperations = new Operation[numberOfAllowedOperations];
        int pos = 0;
        if(generateSum){
            allowedOperations[pos++]=Operation.Sum;
        }
        if(generateDifference){
            allowedOperations[pos++]=Operation.Difference;
        }
        if(generateMultiplication){
            allowedOperations[pos++]=Operation.Multiplication;
        }
        if(generateDivision){
            allowedOperations[pos]=Operation.Division;
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
        if(allowedOperations[randomNum] == Operation.Sum){
            expression.append(num1)
                    .append('+')
                    .append(num2)
                    .append("=?");
            answer = num1+num2;
        }
        else if(allowedOperations[randomNum] == Operation.Difference){
            expression.append(num1)
                    .append('-')
                    .append(num2)
                    .append("=?");
            answer =num1-num2;
        }
        else if(allowedOperations[randomNum] == Operation.Multiplication){
            expression.append(num1)
                    .append('*')
                    .append(num2)
                    .append("=?");
            answer = num1*num2;
        }
        else if(allowedOperations[randomNum] == Operation.Division){
if(num2 == 0){
    num2 = ThreadLocalRandom.current().nextInt(1, maxNumber+1);
}
            expression.append(num1)
                    .append('/')
                    .append(num2)
                    .append("=?");
            answer = num1/num2;
        }
        return new ExpressionTask(expression.toString(), String.valueOf(answer));
    }
}