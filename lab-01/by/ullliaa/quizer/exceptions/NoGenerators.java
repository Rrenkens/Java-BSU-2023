package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class NoGenerators extends RuntimeException {
    public NoGenerators() {
        throw new RuntimeException("Add generators to your test");
    }
}
