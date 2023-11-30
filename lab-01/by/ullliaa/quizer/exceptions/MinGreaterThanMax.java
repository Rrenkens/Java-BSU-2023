package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class MinGreaterThanMax extends ExceptionInInitializerError{
    public MinGreaterThanMax() {
        throw new ExceptionInInitializerError("Min number greater than max number");
    }
}
