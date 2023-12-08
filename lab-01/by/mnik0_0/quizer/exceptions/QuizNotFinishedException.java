package by.mnik0_0.quizer.exceptions;

public class QuizNotFinishedException extends Exception {
    public QuizNotFinishedException() {
        super();
    }

    public QuizNotFinishedException(String message) {
        super(message);
    }

    public QuizNotFinishedException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizNotFinishedException(Throwable cause) {
        super(cause);
    }
}
