package task_generators.math_task_generators;

import tasks.ExpressionTask;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
	AbstractMathTaskGenerator(
        int minNumber,
        int maxNumber,
        boolean generateSum,
        boolean generateDifference,
        boolean generateMultiplication,
        boolean generateDivision
    ) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        operations = new boolean[4];
        this.operations[0] = generateSum;
        this.operations[1] = generateDifference;
        this.operations[2] = generateMultiplication;
        this.operations[3] = generateDivision;
    }
    
    /**
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public int GenerateNumber(int max, int min) {
        return (int)(Math.random()*(max-min+1)+min);  
        
    }
    
    @Override
	public int getMinNumber() {
    	return minNumber;
    }
	
    @Override
    public int getMaxNumber() {
    	return maxNumber;
    }
    
    int minNumber;
    int maxNumber;
    boolean[] operations;
}
