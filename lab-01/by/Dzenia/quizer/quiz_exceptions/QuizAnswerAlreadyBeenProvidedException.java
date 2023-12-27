package by.Dzenia.quizer.quiz_exceptions;

public class QuizAnswerAlreadyBeenProvidedException extends Exception {
    public QuizAnswerAlreadyBeenProvidedException() {
        super("The answer has already been provided!");
    }
}
