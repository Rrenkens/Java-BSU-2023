package by.kirilbaskakov.quizer.tasks.math_tasks;

import java.util.EnumSet;

import by.kirilbaskakov.quizer.exceptions.*;

public class ExpressionMathTask extends AbstractMathTask {
	private final double opLeft;
	private final double opRight;
	private final Operation operation;
	
	public ExpressionMathTask(double opLeft, double opRight, Operation operation, double answer, int precision) {
		if (precision < 0) {
			throw new IllegalArgumentException("Error! Precision must be non-negative");
		}
		this.opLeft = opLeft;
		this.opRight = opRight;
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
	    return opLeft + operand + opRight + "=?";
	}
	
	public static class Generator extends AbstractMathTask.Generator { 
		public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> operations) {
			super(minNumber, maxNumber, precision, operations);
			if (minNumber == maxNumber && minNumber == 0 && !operations.contains(Operation.SUM) && !operations.contains(Operation.DIFF) && !operations.contains(Operation.MUL)) {
				throw new NoPossibleTasksException("Error! Generator can't generate any task");
			}
		}
	    
		@Override
	    public ExpressionMathTask generate() {
	        Operation operation = genRandOperation();
	        double opLeft = genRandNumber();
	        double opRight = genRandNumber();
	        double ans = 0;
	        try {
		        switch (operation) {
		        case SUM:
		        	ans = opLeft + opRight;
		        	break;
		        case DIFF:
		        	ans = opLeft - opRight;
		        	break;
		        case MUL:
		        	ans = opLeft * opRight;
		        	break;
		        case DIV:
		        	ans = opLeft / opRight;
		        	break;
		        }
	        } catch (Exception e) {
	        	// generate again if something went wrong;
	        	return generate();
	        }
	        ans = round(ans);
	        return new ExpressionMathTask(opLeft, opRight, operation, ans, precision);
	    }
	}
}
