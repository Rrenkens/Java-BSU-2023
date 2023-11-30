package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class CantDoGenerator extends ExceptionInInitializerError {
    public CantDoGenerator() {
        throw new RuntimeException("Can't do any operation");
    }
}
