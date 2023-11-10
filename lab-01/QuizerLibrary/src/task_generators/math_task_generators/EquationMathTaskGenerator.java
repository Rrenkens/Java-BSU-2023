package task_generators.math_task_generators;

import tasks.EquationTask;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {

	EquationMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference,
			boolean generateMultiplication, boolean generateDivision) {
		super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
		// TODO Auto-generated constructor stub
	}
	
    @Override
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
    						   Integer.toString(num1) + "+x=" + Integer.toString(num1));
    			} else {
     				return new EquationTask(Integer.toString(num1 + num2),
     						 "x+" + Integer.toString(num1) + "=" + Integer.toString(num1));
    			}

    		case 1:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 - num2),
    						   Integer.toString(num1) + "-x=" + Integer.toString(num1));
    			} else {
     				return new EquationTask(Integer.toString(num1 - num2),
     						 "x-" + Integer.toString(num1) + "=" + Integer.toString(num1));
    			}
    		case 2:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 * num2),
    						   Integer.toString(num1) + "*x=" + Integer.toString(num1));
    			} else {
     				return new EquationTask(Integer.toString(num1 * num2),
     						 "x*" + Integer.toString(num1) + "=" + Integer.toString(num1));
    			}
    		default:
    			if (IsLeft) {
    				return new EquationTask(Integer.toString(num1 / num2),
    						   Integer.toString(num1) + "/x=" + Integer.toString(num1));
    			} else {
     				return new EquationTask(Integer.toString(num1 / num2),
     						 "x/" + Integer.toString(num1) + "=" + Integer.toString(num1));
    			}
    	}
    }
    
    boolean IsLeft;
}
