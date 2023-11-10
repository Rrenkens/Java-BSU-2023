package task_generators;

import tasks.EquationTask;

public class EquationTaskGenerator implements TaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
	public EquationTaskGenerator(
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
    
    public int GenerateNumber(int max, int min) {
        return (int)(Math.random()*(max-min+1)+min);  
        
    }
    
    public EquationTask generate() {
    	int num1 = GenerateNumber(maxNumber, minNumber);
    	int num2 = GenerateNumber(maxNumber, minNumber);
    	int operationNumber = GenerateNumber(3, 0);
    	//int k = -1;
    	for (int i = 0, cnt = 0;i < 3; ++i) {
    		if (operations[i]) {
    			//k = i;
    			cnt++;
        		if (cnt == operationNumber) {
        			operationNumber = i;
        			break;
        		}
        		operationNumber = i;
    		}
    	}
    	int left = GenerateNumber(0,1);
    	if (left == 0) {
    		IsLeft = true;
    	} else {
    		IsLeft = false;
    	}
    	
    	switch (operationNumber) {
    		case 0:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 + num2),
    						   Integer.toString(num1) + "+x=" + Integer.toString(num2));
    			} else {
     				return new EquationTask(Integer.toString(num1 + num2),
     						 "x+" + Integer.toString(num1) + "=" + Integer.toString(num2));
    			}

    		case 1:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 - num2),
    						   Integer.toString(num1) + "-x=" + Integer.toString(num2));
    			} else {
     				return new EquationTask(Integer.toString(num1 - num2),
     						 "x-" + Integer.toString(num1) + "=" + Integer.toString(num2));
    			}
    		case 2:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 * num2),
    						   Integer.toString(num1) + "*x=" + Integer.toString(num2));
    			} else {
     				return new EquationTask(Integer.toString(num1 * num2),
     						 "x*" + Integer.toString(num1) + "=" + Integer.toString(num2));
    			}
    		default:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 / num2),
    						   Integer.toString(num1) + "/x=" + Integer.toString(num2));
    			} else {
     				return new EquationTask(Integer.toString(num1 / num2),
     						 "x/" + Integer.toString(num1) + "=" + Integer.toString(num2));
    			}
    	}
    }
    boolean IsLeft;
    int minNumber;
    int maxNumber;
    boolean[] operations;
}
