package by.ullliaa.quizer.by.ullliaa.quizer.exceptions;

public class CantGenerateTask extends RuntimeException {
    public CantGenerateTask() throws Exception {
        throw new RuntimeException("Can't generate new task");
    }
}
