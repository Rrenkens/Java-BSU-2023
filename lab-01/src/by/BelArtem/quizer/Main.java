package by.BelArtem.quizer;

import by.BelArtem.quizer.exceptions.QuizNotFinishedException;
import by.BelArtem.quizer.task_generators.*;
import by.BelArtem.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.BelArtem.quizer.task_generators.math_task_generators.ExpressionMathTaskGenerator;
import by.BelArtem.quizer.tasks.ExpressionTask;
import by.BelArtem.quizer.tasks.TextTask;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Available quizzes:");
        Map<String, Quiz> map = getQuizMap();
        for (String name: map.keySet()){
            System.out.print("-");
            System.out.println(name);
        }

        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("\nEnter quiz name");
        userInput = scanner.nextLine();
        while (!map.containsKey(userInput)){
            System.out.println("Wrong quiz name, try again");
            userInput = scanner.nextLine();
        }

        Quiz quiz = map.get(userInput);
        System.out.println("This quiz has " + quiz.getTaskCount() + " tasks");
        Task currentTask;
        boolean isOk = true;

        while (!quiz.isFinished()){
            try {
                currentTask = quiz.nextTask();
            } catch (Exception e) {
                System.out.print("\nException has been caught:");
                System.out.println(e.getMessage());
                isOk = false;
                break;
            }

            System.out.println("\nGiven task is: " + currentTask.getText());
            userInput = scanner.nextLine();
            Result res = quiz.provideAnswer(userInput);
            switch (res) {
                case INCORRECT_INPUT -> System.out.println("Incorrect answer was given, try again");
                case WRONG -> System.out.println("Wrong answer. Don't worry, making mistakes is a necessary part " +
                        "of learning");
                case OK -> System.out.println("Correct!");
            }
        }

        if (isOk){
            double mark = -1;
            try {
                mark = quiz.getMark() * 10;
            } catch (QuizNotFinishedException e) {
                System.out.println("Unexpected error: can not calculate mark");
            }
            System.out.println("\nQuiz is finished, your results are:");
            System.out.println("Your mark: " + mark);
            System.out.println("Number of correct answers: " + quiz.getCorrectAnswerNumber());
            System.out.println("Number of wrong answers: " + quiz.getWrongAnswerNumber());
            System.out.println("Number of incorrect inputs: " + quiz.getIncorrectInputNumber());
        } else {
            System.out.println("\nQuiz is finished, but something went wrong(");
        }
    }

    static Map<String, Quiz> getQuizMap(){
        Map<String, Quiz> map = new HashMap<>();

        /** TEST BLOCK #1 (Testing Quiz with EquationTaskGenerator)
         *--------------------------------------------------------------------*/

        /** Expecting normal behavior*/
        TaskGenerator equationTaskGen_0 = new EquationTaskGenerator(-10, 10,
                true, true, true, true);
        Quiz equationQuiz_0 = new Quiz(equationTaskGen_0, 10);
        map.put("Equation quiz", equationQuiz_0);

        /** Expecting exception to be thrown*/
        TaskGenerator equationTaskGen_1 = new EquationTaskGenerator(-10, 10,
                false, false, false, false);
        Quiz equationQuiz_1 = new Quiz(equationTaskGen_1, 10);
        map.put("Equation generator with all flags equal to false", equationQuiz_1);

        /** Expecting exception to be thrown*/
        TaskGenerator equationTaskGen_2 = new EquationTaskGenerator(0, 0,
                false, false, false, true);
        Quiz equationQuiz_2 = new Quiz(equationTaskGen_2, 10);
        map.put("Equation with senseless input data", equationQuiz_2);

        /**--------------------------------------------------------------------*/


        /** TEST BLOCK #2 (Testing Quiz with ExpressionTaskGenerator)
         *--------------------------------------------------------------------*/

        /** Expecting normal behavior*/
        TaskGenerator expressionTaskGen_0 = new ExpressionTaskGenerator(-10, 10,
                true, true, true, true);
        Quiz expressionQuiz_0 = new Quiz(expressionTaskGen_0, 10);
        map.put("Expression quiz", expressionQuiz_0);

        /** Expecting exception to be thrown*/
        TaskGenerator expressionTaskGen_1 = new ExpressionTaskGenerator(-10, 10,
                false, false, false, false);
        Quiz expressionQuiz_1 = new Quiz(expressionTaskGen_1, 10);
        map.put("Expression generator with all flags equal to false", expressionQuiz_1);

        /** Expecting exception to be thrown*/
        TaskGenerator expressionTaskGen_2 = new ExpressionTaskGenerator(0, 0,
                false, false, false, true);
        Quiz expressionQuiz_2 = new Quiz(expressionTaskGen_2, 10);
        map.put("Expression with senseless input data", expressionQuiz_2);

        /**--------------------------------------------------------------------*/


        /** TEST BLOCK #3 (Testing Quiz with EquationMathTaskGenerator)
         *--------------------------------------------------------------------*/

        /** Expecting normal behavior*/
        TaskGenerator equationMathTaskGen_0 = new EquationMathTaskGenerator(-10, 10,
                true, true, true, true);
        Quiz equationMathQuiz_0 = new Quiz(equationMathTaskGen_0, 10);
        map.put("Equation quiz", equationMathQuiz_0);

        /** Expecting exception to be thrown*/
        TaskGenerator equationMathTaskGen_1 = new EquationMathTaskGenerator(-10, 10,
                false, false, false, false);
        Quiz EquationMathQuiz_1 = new Quiz(equationMathTaskGen_1, 10);
        map.put("EquationMath generator with all flags equal to false", EquationMathQuiz_1);

        /** Expecting exception to be thrown*/
        TaskGenerator equationMathTaskGen_2 = new EquationMathTaskGenerator(0, 0,
                false, false, false, true);
        Quiz EquationMathQuiz_2 = new Quiz(equationMathTaskGen_2, 10);
        map.put("EquationMath with senseless input data", EquationMathQuiz_2);

        /**--------------------------------------------------------------------*/


        /** TEST BLOCK #4 (Testing Quiz with ExpressionMathTaskGenerator)
         *--------------------------------------------------------------------*/

        /** Expecting normal behavior*/
        TaskGenerator expressionMathTaskGen_0 = new ExpressionMathTaskGenerator(-10, 10,
                true, true, true, true);
        Quiz expressionMathQuiz_0 = new Quiz(expressionMathTaskGen_0, 10);
        map.put("ExpressionMath quiz", expressionMathQuiz_0);

        /** Expecting exception to be thrown*/
        TaskGenerator expressionMathTaskGen_1 = new ExpressionMathTaskGenerator(-10, 10,
                false, false, false, false);
        Quiz expressionMathQuiz_1 = new Quiz(expressionMathTaskGen_1, 10);
        map.put("ExpressionMath generator with all flags equal to false", expressionMathQuiz_1);

        /** Expecting exception to be thrown*/
        TaskGenerator expressionMathTaskGen_2 = new ExpressionMathTaskGenerator(0, 0,
                false, false, false, true);
        Quiz expressionMathQuiz_2 = new Quiz(expressionMathTaskGen_2, 10);
        map.put("ExpressionMath with senseless input data", expressionMathQuiz_2);

        /**--------------------------------------------------------------------*/


        /** TEST BLOCK #5 (Testing Quiz with PoolTaskGenerator)
         *--------------------------------------------------------------------*/

        Collection<Task> tasksForPoolGenerator = new ArrayList<>();

        Task textTask1 = new TextTask("Is the Elon Mask a billioner?", "Yes");
        Task textTask2 = new TextTask("What is the name of the closest Galaxy to the Milky Way?", "Andromeda");
        Task textTask3 = new TextTask("What is the name of the closest star to our home planet?", "Sun");
        Task exprTask1 = new ExpressionTask("5! = ?", 120);

        tasksForPoolGenerator.add(textTask1);
        tasksForPoolGenerator.add(textTask2);
        tasksForPoolGenerator.add(textTask3);
        tasksForPoolGenerator.add(exprTask1);


        /** Expecting normal behavior*/
        TaskGenerator poolTaskGen_0 = new PoolTaskGenerator(
                true,
                textTask1,
                textTask2,
                textTask3,
                exprTask1
        );
        Quiz poolQuiz_0 = new Quiz(poolTaskGen_0, 6);
        map.put("Normal Pool_0 quiz", poolQuiz_0);


        /** Expecting normal behavior*/
        TaskGenerator poolTaskGen_1 = new PoolTaskGenerator(false, tasksForPoolGenerator);
        Quiz poolQuiz_1 = new Quiz(poolTaskGen_1, 3);
        map.put("Normal Pool_1 quiz", poolQuiz_1);

        /** Expecting exception to be thrown*/
        TaskGenerator poolTaskGen_2 = new PoolTaskGenerator(false, tasksForPoolGenerator);
        Quiz poolQuiz_2 = new Quiz(poolTaskGen_2, 5);
        map.put("Exception pool quiz", poolQuiz_2);

        /**--------------------------------------------------------------------*/


        /** TEST BLOCK #6 (Testing Quiz with GroupTaskGenerator)
         *--------------------------------------------------------------------*/

        /** Expecting normal behavior*/
        TaskGenerator equationTaskGen_3 = new EquationTaskGenerator(-10, 10,
                true, false, false, false);

        TaskGenerator expressionMathTaskGen_3 = new ExpressionMathTaskGenerator(-10, 10,
                false, true, false, false);

        /** Generate either equation with sum, or expression with difference*/
        TaskGenerator groupGen_0 = new GroupTaskGenerator(equationTaskGen_3, expressionMathTaskGen_3);
        Quiz groupQuiz_0 = new Quiz(groupGen_0, 5);
        map.put("Group task quiz", groupQuiz_0);


        /** Expecting exception to be thrown*/
        TaskGenerator equationTaskGen_4 = new EquationTaskGenerator(-10, 10,
                false, false, false, false);

        TaskGenerator expressionMathTaskGen_4 = new ExpressionMathTaskGenerator(-10, 10,
                false, false, false, false);

        TaskGenerator groupGen_1 = new GroupTaskGenerator(equationTaskGen_4, expressionMathTaskGen_4);
        Quiz groupQuiz_1 = new Quiz(groupGen_1, 5);
        map.put("Group task exception quiz", groupQuiz_1);

        /**--------------------------------------------------------------------*/

        return map;
    }
}
