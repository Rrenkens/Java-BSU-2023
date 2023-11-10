package task_generators.math_task_generators;

import tasks.ExpressionTask;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {

	ExpressionMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference,
			boolean generateMultiplication, boolean generateDivision) {
		super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public ExpressionTask generate() {
    	int num1 = GenerateNumber(maxNumber, minNumber);
    	int num2 = GenerateNumber(maxNumber, minNumber);
    	int operationNumber = GenerateNumber(3, 0);
    	switch (operationNumber) {
    		case 0:
    			return new ExpressionTask(Integer.toString(num1 + num2),
    				   Integer.toString(num1) + "+" + Integer.toString(num2) + "=?");
    		case 1:
    			return new ExpressionTask(Integer.toString(num1 - num2),
    				   Integer.toString(num1) + "-" + Integer.toString(num2) + "=?");
    		case 2:
    			return new ExpressionTask(Integer.toString(num1 * num2),
    				   Integer.toString(num1) + "*" + Integer.toString(num2) + "=?");
    		default:
    			while (num2 == 0) {
    				num2 = GenerateNumber(maxNumber, minNumber);
    			}
    			return new ExpressionTask(Integer.toString(num1 / num2),
    				   Integer.toString(num1) + "*" + Integer.toString(num2) + "=?");
    	}
    }

}
