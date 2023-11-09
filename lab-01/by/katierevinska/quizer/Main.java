package by.katierevinska.quizer;
import by.katierevinska.quizer.task_generators.GroupTaskGenerator;
import by.katierevinska.quizer.task_generators.PoolTaskGenerator;
import by.katierevinska.quizer.tasks.math_tasks.EquationTask;
import by.katierevinska.quizer.tasks.math_tasks.ExpressionTask;
import by.katierevinska.quizer.tasks.math_tasks.MathTask;
import by.katierevinska.quizer.tasks.math_tasks.PlatesProblemTask;

import java.util.*;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
        EnumSet<MathTask.Operation> sumAndSubOperations = EnumSet.of(MathTask.Operation.Sum, MathTask.Operation.Difference);
        EnumSet<MathTask.Operation> multiplicationAndDivisionOperations = EnumSet.of(MathTask.Operation.Multiplication, MathTask.Operation.Division);
        PoolTaskGenerator poolGenerator= new PoolTaskGenerator (false,new PlatesProblemTask.Generator(5, 12).generate(), new ExpressionTask.Generator(15, 20, operations).generate());
        poolGenerator.generate().getText();
        poolGenerator.generate().getText();
       // poolGenerator.generate().getText();
        PoolTaskGenerator poolGeneratorWithDuplicates= new PoolTaskGenerator (true,new PlatesProblemTask.Generator(5, 12).generate(), new ExpressionTask.Generator(15, 20, operations).generate());
        poolGeneratorWithDuplicates.generate().getText();
        poolGeneratorWithDuplicates.generate().getText();
        poolGeneratorWithDuplicates.generate().getText();
        Map<String, Quiz> quizMap = new HashMap<>();

        try {
            quizMap.put ("ExpressionTask 1", new Quiz(new ExpressionTask.Generator(0, 10, 0,
                    sumAndSubOperations), 5));
            quizMap.put ("ExpressionTask 2", new Quiz(new ExpressionTask.Generator(-10, 10,
                    multiplicationAndDivisionOperations), 5));
            quizMap.put ("ExpressionTask 3", new Quiz(new ExpressionTask.Generator(-5, 10,
                    operations), 5));
            quizMap.put ("EquationTask 1", new Quiz(new EquationTask.Generator(0, 15,//TODO another equal for double
                    operations), 10));
            quizMap.put ("EquationTask 2", new Quiz(new EquationTask.Generator(-5, 10,
                    multiplicationAndDivisionOperations), 1));
           quizMap.put ("EquationTask 3", new Quiz(new EquationTask.Generator(-7, 7,
                    operations), 0));

            quizMap.put ("ExpressionTask 1 with precision 1", new Quiz(new ExpressionTask.Generator(-5, 10, 1,
                    sumAndSubOperations), 5));
            quizMap.put ("EquationTask 1 with precision 1", new Quiz(new EquationTask.Generator(0, 15, 1,//TODO another equal for double
                    operations), 10));
            quizMap.put ("EquationTask 2 with precision 2", new Quiz(new EquationTask.Generator(-5, 10, 2,
                    operations), 1));
         quizMap.put ("PlatesProblem", new Quiz(new PlatesProblemTask.Generator(1, 8),2));
         quizMap.put ("GroupGenerator", new Quiz(new GroupTaskGenerator(new PlatesProblemTask.Generator(6, 12),new ExpressionTask.Generator(15, 20, sumAndSubOperations)), 3));
         quizMap.put ("GroupGenerator (with exception)", new Quiz(new GroupTaskGenerator(new PlatesProblemTask.Generator(0, 12),new ExpressionTask.Generator(15, 20, operations)), 3));

            //TODO not all operations in test
        } catch (Exception e) {
//TODO       throw new AssertionError("Метод работает неверно!");
  //            System.out.println(e.getLocalizedMessage());

        }

        return quizMap;
    }

    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Введите название теста...");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        while(!quizMap.containsKey(name)){
            System.out.println("Повторите попытку, тест с указанным названием не найден...");
            name = scanner.nextLine();
        }
        Quiz quiz = quizMap.get(name);
        while(!quiz.isFinished()){
            System.out.println(quiz.nextTask().getText());
            System.out.println("Введите ответ c требуемой точностью...");
            Result result = quiz.provideAnswer(scanner.nextLine());
            if(result == Result.INCORRECT_INPUT) {
                System.out.println("Некорректный ввод, попробуйте ещё раз...");
            }else if(result == Result.WRONG){
                System.out.println("Неверно, будьте вниметельнее");
            }else if(result == Result.OK){
                System.out.println("Молодец");
            }
        }
        System.out.println("Тест окончен, ваша отметка " + String.format("%.2f", quiz.getMark())+"\nВерных ответов: "+quiz.getCorrectAnswerNumber()+"\nКоличество неверных ответов: "+quiz.getWrongAnswerNumber());
    }


}
