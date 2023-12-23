package by.arteman17.quizer.exceptions;

public class QuizNotFinished extends RuntimeException {
    public QuizNotFinished(String message) {
        super(message);
    }
}
