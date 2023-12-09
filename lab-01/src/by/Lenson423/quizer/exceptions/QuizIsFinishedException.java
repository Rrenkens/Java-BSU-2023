package by.Lenson423.quizer.exceptions;

public class QuizIsFinishedException extends RuntimeException{
    public QuizIsFinishedException(String text) {
        super(text);
    }
}
