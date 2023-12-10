package by.kirilbaskakov.quizer.tasks.math_tasks;

import by.kirilbaskakov.quizer.Result;

import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {
	protected double answer;
	protected int precision;
	
	@Override
	public Result validate(String answer) {
	 try {
		 double result = Double.parseDouble(answer);
		 return Math.abs(this.answer - result) < Math.pow(10, -precision) ? Result.OK : Result.WRONG;
	 } catch (NumberFormatException e) {
	     return Result.INCORRECT_INPUT;
	 }
	}
	
	static abstract class Generator implements MathTask.Generator { 
		private final double minNumber;
		private final double maxNumber;
		private final EnumSet<Operation> operations;
		
		protected final int precision;
			
		Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> operations) {
			if (minNumber > maxNumber) {
				throw new IllegalArgumentException("Error! Min number nust me greater than max number");
			}
			if (operations.isEmpty()) {
				throw new IllegalArgumentException("Error! Set of operations must be non-empty");
			}
			if (precision < 0) {
				throw new IllegalArgumentException("Error! Precision must be non-negative");
			}
			this.minNumber = minNumber;
			this.maxNumber = maxNumber;
			this.precision = precision;
			this.operations = operations;
		}
		
		@Override 
		public double getMinNumber() {
			return minNumber;
		}
		
		@Override 
		public double getMaxNumber() {
			return maxNumber;
		}
		
		protected double genRandNumber() {
			double randomDouble = minNumber + new Random().nextDouble() * getDiffNumber();
			return round(randomDouble);
		}
		
		protected Operation genRandOperation() {
			Operation[] values = operations.toArray(new Operation[0]);
			Operation randomElement = values[new Random().nextInt(values.length)];
			return randomElement;
		}
		
		protected double round(double number) {
			return Double.parseDouble(String.format("%." + precision + "f", number).replace(',', '.'));
		}
	}
}
