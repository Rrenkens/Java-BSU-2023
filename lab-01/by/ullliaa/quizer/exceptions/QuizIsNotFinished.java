package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class QuizIsNotFinished extends RuntimeException {
    public QuizIsNotFinished() {
        throw new RuntimeException("Quiz is not finished");
    }
}
