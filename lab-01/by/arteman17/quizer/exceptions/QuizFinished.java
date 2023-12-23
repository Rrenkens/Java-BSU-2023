package by.arteman17.quizer.exceptions;

public class QuizFinished extends RuntimeException {
    public QuizFinished(String message) {
        super(message);
    }
}
