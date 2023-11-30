package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class NoTasks extends RuntimeException {
    public NoTasks() {
        throw new RuntimeException("Add tasks to test");
    }
}
