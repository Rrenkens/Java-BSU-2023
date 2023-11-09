package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public abstract class AbstractMathTask implements MathTask {

    public abstract static class Generator implements MathTask.Generator{
        public static double generatingDoubleWithPrecision(double minNumber, double maxNumber, int precision){
        double num1 = ((int)ThreadLocalRandom.current().nextDouble(minNumber, maxNumber+1)*pow(10, precision))*1.0/pow(10, precision);
        //return String.format("%."+precision +"f", num1);

            return num1;
    }
        public static double generationWithout0(double minNumber, double maxNumber, int precision){
            if(maxNumber<0){
                return generatingDoubleWithPrecision(minNumber, 0, precision);
            }else{
                return generatingDoubleWithPrecision(1, maxNumber, precision);
            }
        }
        public static String formationWithBracket(double num2){
            return num2 <0?"("+num2+")": String.valueOf(num2);
        }
    }

    protected String text;
    protected String answer;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        answer =answer.replace(',', '.');
        this.answer = this.answer.replace(',', '.');
        try {
            double d = Double.parseDouble(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        String[] str= this.answer.split("\\.");
        int precision;
        if(str.length == 1){
            precision = 0;
        }else{
            precision = str[1].length();
        }
        if(abs(Double.parseDouble(this.answer) -Double.parseDouble(answer)) < pow(0.1, precision)){
            return Result.OK;
        }
        return Result.WRONG;
    }
}
