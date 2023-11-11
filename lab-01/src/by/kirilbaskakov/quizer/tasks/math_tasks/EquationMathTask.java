package by.kirilbaskakov.quizer.tasks.math_tasks;

import java.util.EnumSet;
import java.util.Random;

import by.kirilbaskakov.quizer.exceptions.NoPossibleTasksException;

public class EquationMathTask extends AbstractMathTask {	
	private final double num;
	private final double result;
	private final boolean isLeft;
	private final Operation operation;
	
	public EquationMathTask(double num, double result, boolean isLeft, Operation operation, double answer, int precision) {
		if (precision < 0) {
			throw new IllegalArgumentException("Error! Precision must be non-negative");
		}
		this.num = num;
		this.result = result;
		this.isLeft = isLeft;
		this.operation = operation;
		this.answer = answer;
		this.precision = precision;
	}
	
	@Override
	public String getText() {
		String operand = "";
	    switch (operation) {
	        case SUM:
	            operand = "+";
	            break;
	        case DIFF:
	            operand = "-";
	            break;
	        case MUL:
	            operand = "*";
	            break;
	        case DIV:
	            operand = "/";
	            break;
	    }
	    return isLeft ?  "x" + operand + num + "=" + result : num + operand + "x" + "=" + result;
	}
	
	public static class Generator extends AbstractMathTask.Generator { 
		public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> operations) {
			super(minNumber, maxNumber, precision, operations);
			if (minNumber == maxNumber && minNumber == 0 && !operations.contains(Operation.SUM) && !operations.contains(Operation.DIFF) && !operations.contains(Operation.DIV)) {
				throw new NoPossibleTasksException("Error! Generator can't generate any task");
			}
		}
		
		@Override
		public EquationMathTask generate() {
	        Operation operation = genRandOperation();
	        double num = genRandNumber();
	        double result = genRandNumber();
	        boolean isLeft = new Random().nextBoolean();
	        double ans = 0;
	        try {
		        switch (operation) {
		        case SUM:
		        	ans = result - num;
		        	break;
		        case DIFF:
		        	ans = isLeft ? result + num : -result + num;
		        	break;
		        case MUL:
		        	ans = result / num;
		        	break;
		        case DIV:
		        	ans = isLeft ? result * num : num / result;
		        	break;
		        }
	        } catch (Exception e) {
	        	// generate again if something went wrong;
	        	return generate();
	        }
	        ans = round(ans);
	        return new EquationMathTask(num, result, isLeft, operation, ans, precision);
	    }
	}
}
