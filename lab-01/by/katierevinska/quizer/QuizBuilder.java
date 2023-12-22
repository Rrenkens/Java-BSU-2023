package by.katierevinska.quizer;

import by.katierevinska.quizer.task_generators.GroupTaskGenerator;
import by.katierevinska.quizer.task_generators.PoolTaskGenerator;
import by.katierevinska.quizer.tasks.TextTask;
import by.katierevinska.quizer.tasks.math_tasks.EquationTask;
import by.katierevinska.quizer.tasks.math_tasks.ExpressionTask;
import by.katierevinska.quizer.tasks.math_tasks.MathTask;
import by.katierevinska.quizer.tasks.math_tasks.PlatesProblemTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class QuizBuilder {
    private String quizList = """
                ExpressionTask 1
                ExpressionTask 2
                ExpressionTask 3
                ExpressionTask 1 with precision 1
                EquationTask 1
                EquationTask 2
                EquationTask 1 with precision 1
                EquationTask 2 with precision 2
                PlatesProblem
                GroupGenerator
                GroupGenerator (with exception)
                GroupGenerator (with exception) 2
                Pool3
                Pool4
                """;
    public String getQuizList(){
        return quizList;
    }
    public Map<String, Quiz> getQuizMap() {
        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
        EnumSet<MathTask.Operation> sumAndSubOperations =
                EnumSet.of(MathTask.Operation.SUM, MathTask.Operation.DIFFERENCE);
        EnumSet<MathTask.Operation> multiplicationAndDivisionOperations =
                EnumSet.of(MathTask.Operation.MULTIPLICATION, MathTask.Operation.DIVISION);
        try {
            PoolTaskGenerator poolGenerator =
                    new PoolTaskGenerator(false,
                            new PlatesProblemTask.Generator(5, 12).generate(),
                            new ExpressionTask.Generator(15, 20, operations).generate());
            poolGenerator.generate().getText();
            poolGenerator.generate().getText();
            // poolGenerator.generate().getText();//throw exception +
            PoolTaskGenerator poolGeneratorWithDuplicates =
                    new PoolTaskGenerator(true,
                            new PlatesProblemTask.Generator(5, 12).generate(),
                            new ExpressionTask.Generator(15, 20, operations).generate());
            poolGeneratorWithDuplicates.generate().getText();
            poolGeneratorWithDuplicates.generate().getText();
            poolGeneratorWithDuplicates.generate().getText();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        Map<String, Quiz> quizMap = new HashMap<>();

        try {
//            quizMap.put("Pool1", new Quiz(new GroupTaskGenerator(new PoolTaskGenerator(false, new PlatesProblemTask.Generator(5, 12).generate(),
//                    new ExpressionTask.Generator(15, 20, operations).generate())),3));//throw except +
            quizMap.put("Pool3", new Quiz(new PoolTaskGenerator(true,
                    new PlatesProblemTask.Generator(5, 12).generate(),
                    new ExpressionTask.Generator(15, 20, operations).generate()), 4));
            quizMap.put("Pool4", new Quiz(new PoolTaskGenerator(false,
                    new TextTask("Какого цвета молоко?", "Белый"),
                    new TextTask("Какое дерево - символ нового года?", "Ёлка")), 2));
            quizMap.put("ExpressionTask 1", new Quiz(
                    new ExpressionTask.Generator(0, 10, 0, sumAndSubOperations), 5));

            quizMap.put("ExpressionTask 2", new Quiz(
                    new ExpressionTask.Generator(-10, 10, multiplicationAndDivisionOperations), 5));
            quizMap.put("ExpressionTask 3", new Quiz(
                    new ExpressionTask.Generator(-5, 10, operations), 5));
            quizMap.put("EquationTask 1", new Quiz(
                    new EquationTask.Generator(0, 15, operations), 10));
            quizMap.put("EquationTask 2", new Quiz(
                    new EquationTask.Generator(-5, 10,  multiplicationAndDivisionOperations), 1));
//           quizMap.put ("EquationTask 3", new Quiz(new EquationTask.Generator(-7, 7,
//                    operations), 0));//throw exception +

            quizMap.put("ExpressionTask 1 with precision 1", new Quiz(
                    new ExpressionTask.Generator(-5, 10, 1, sumAndSubOperations), 5));
            quizMap.put("EquationTask 1 with precision 1", new Quiz(
                    new EquationTask.Generator(0, 3, 1, multiplicationAndDivisionOperations), 10));
            quizMap.put("EquationTask 2 with precision 2", new Quiz(
                    new EquationTask.Generator(-5, 10, 2, operations), 1));
            quizMap.put("PlatesProblem", new Quiz(new PlatesProblemTask.Generator(1, 8), 2));
//       quizMap.put ("PlatesProblem (with exception)", new Quiz(new PlatesProblemTask.Generator(0, 8),2));//throw exception +
            quizMap.put("GroupGenerator", new Quiz(new GroupTaskGenerator(
                    new PlatesProblemTask.Generator(6, 12),
                    new ExpressionTask.Generator(15, 20, sumAndSubOperations)), 3));
            quizMap.put("GroupGenerator (with exception)", new Quiz(new GroupTaskGenerator(
                    new ExpressionTask.Generator(9, 0, operations),
                    new ExpressionTask.Generator(15, 20, operations)), 3));
            quizMap.put("GroupGenerator (with exception) 2", new Quiz(new GroupTaskGenerator(
                    new PlatesProblemTask.Generator(0, 12),
                    new ExpressionTask.Generator(15, 20, operations)), 3));

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return quizMap;
    }
}
