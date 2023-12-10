package by.kirilbaskakov.quizer.exceptions;

public class QuizNotFinishedException extends RuntimeException {
    public QuizNotFinishedException(String text) {
        super(text);
    }
}