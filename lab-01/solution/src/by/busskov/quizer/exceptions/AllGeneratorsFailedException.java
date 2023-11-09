package by.busskov.quizer.exceptions;

public class AllGeneratorsFailedException extends GenerateException {
    public AllGeneratorsFailedException() {
        super();
    }

    public AllGeneratorsFailedException(String message) {
        super(message);
    }

    public AllGeneratorsFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AllGeneratorsFailedException(Throwable cause) {
        super(cause);
    }

    protected AllGeneratorsFailedException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
