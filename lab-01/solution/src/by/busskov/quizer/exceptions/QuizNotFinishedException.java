package by.busskov.quizer.exceptions;

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

    protected QuizNotFinishedException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
