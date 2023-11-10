package by.rycbaryana.quizer;

import by.rycbaryana.quizer.exceptions.QuizNotFinishedException;
import by.rycbaryana.quizer.tasks.Task;
import by.rycbaryana.quizer.task_generators.TaskGenerator;
import by.rycbaryana.quizer.tasks.TextTask;
import by.rycbaryana.quizer.tasks.math_tasks.ExpressionTask;
import by.rycbaryana.quizer.tasks.math_tasks.EquationTask;
import by.rycbaryana.quizer.tasks.math_tasks.Operation;
import by.rycbaryana.quizer.task_generators.GroupTaskGenerator;
import by.rycbaryana.quizer.task_generators.PoolTaskGenerator;
import by.rycbaryana.quizer.tasks.math_tasks.TriangleAreaTask;

import java.util.*;


/**
 * Class, который описывает один тест
 */
class Quiz {
    TaskGenerator generator;
    int taskCount;
    int wrongCount = 0;
    int okCount = 0;
    int incorrectInputCount = 0;
    Task currentTask = null;
    boolean repeatTask = false;


    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (repeatTask) {
            repeatTask = false;
        } else {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(Answer answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK -> okCount++;
            case WRONG -> wrongCount++;
            case INCORRECT_INPUT -> {
                incorrectInputCount++;
                repeatTask = true;
            }
        }
        return result;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return wrongCount + okCount == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return okCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Quiz is not finished");
        } else {
            return 10 * (double) okCount / taskCount;
        }
    }
}

public class Quizer {
    static Map<String, Quiz> getQuizMap() {
        HashMap<String, Quiz> quizMap = new HashMap<>();
        TaskGenerator expression = new ExpressionTask.Generator(EnumSet.allOf(Operation.class), -10, 10, 0);
        TaskGenerator equation = new EquationTask.Generator(EnumSet.allOf(Operation.class), -10, 10, 0);
        quizMap.put("Exp", new Quiz(expression, 5));
        quizMap.put("Eq", new Quiz(equation, 5));
        quizMap.put("Math", new Quiz(new GroupTaskGenerator(expression, equation), 10));
        ArrayList<Task> questions = new ArrayList<>(List.of(
                new TextTask("Сумма квадратов катетов равна квадрату ...", "гипотенузы"),
                new TextTask("Зимой и летом одним цветом", "тридцать три"),
                new TextTask("Сколько букв в слове а?", "10"),
                new TextTask("What is love?", "Baby don't hurt me"),
                new TextTask("Placeholder question?", "Placeholder answer")
        )
        );
        TaskGenerator pool = new PoolTaskGenerator(false, questions);
        quizMap.put("Quiz", new Quiz(pool, 5));
        quizMap.put("Triangles", new Quiz(new TriangleAreaTask.Generator(1, 10, 1), 5));
        return quizMap;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available quizes: ");
        var quizMap = getQuizMap();
        for (String name : quizMap.keySet()) {
            System.out.println(name);
        }
        Quiz quiz = null;
        while (quiz == null) {
            System.out.print("Enter quiz name: ");
            String quizName = scanner.nextLine();
            quiz = quizMap.get(quizName);
            if (quiz == null) {
                System.out.println("There is no test named \"" + quizName + "\". Try again.");
            }
        }
        while (!quiz.isFinished()) {
            Task task;
            try {
                task = quiz.nextTask();
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            System.out.println(task.getText());
            Answer answer = new Answer(scanner.nextLine());
            if (quiz.provideAnswer(answer) == Result.INCORRECT_INPUT) {
                System.out.printf("Incorrect input. %s is expected.\n", answer.isNumeric() ? "Text" : "Number");
            }
        }
        try {
            System.out.println("Test finished! Your mark is " + quiz.getMark());
        } catch (QuizNotFinishedException e) {
            System.out.println(e.getMessage());
        }
    }
}
