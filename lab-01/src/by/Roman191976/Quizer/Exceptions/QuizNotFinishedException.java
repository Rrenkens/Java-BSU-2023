package by.Roman191976.Quizer.Exceptions;

public class QuizNotFinishedException extends RuntimeException {
    public QuizNotFinishedException(String text) {
        super(text);
    }
}