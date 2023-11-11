package by.kirilbaskakov.quizer.exceptions;

public class AllGeneratorsFailedException extends RuntimeException {
    public AllGeneratorsFailedException(String text) {
        super(text);
    }
}

