package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.tasks.TextTask;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.pow;

public abstract class AbstractMathTask implements MathTask {

    public abstract static class Generator implements MathTask.Generator{
        public static String generatingDoubleWithPrecision(double minNumber, double maxNumber, int precision){
        double num1 = ThreadLocalRandom.current().nextDouble(minNumber, maxNumber+1);
        return String.format("%."+precision +"f", num1);
    }
        public static String generationWithout0(double minNumber, double maxNumber, int precision){
            if(maxNumber<0){
                return generatingDoubleWithPrecision(minNumber, 0, precision);
            }else{
                return generatingDoubleWithPrecision(1, maxNumber, precision);
            }
        }
        public static String formationWithBracket(String num2){
            return Double.parseDouble(num2) <0?"("+num2+")": num2;
        }
    }

    String text;
    String answer;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        try {
            double d = Double.parseDouble(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        String[] str= this.answer.split("\\.");//TODO it is wierd
        int precision;
        if(str.length == 1){
            precision = 0;
        }else{
            precision = str[1].length();
        }
        if(Double.parseDouble(this.answer) -Double.parseDouble(answer) < pow(0.1, precision)){
            return Result.OK;
        }
        return Result.WRONG;
    }
}
