package by.busskov.quizer.exceptions;

public abstract class GenerateException extends RuntimeException {
    public GenerateException() {
        super();
    }

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateException(Throwable cause) {
        super(cause);
    }

    protected GenerateException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
