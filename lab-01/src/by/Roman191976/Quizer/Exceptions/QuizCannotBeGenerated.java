package by.Roman191976.Quizer.Exceptions;

public class QuizCannotBeGenerated extends RuntimeException {
    public QuizCannotBeGenerated(String text) {
        super(text);
    }
}