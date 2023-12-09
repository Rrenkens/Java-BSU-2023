package by.Katya841.quizer.exceptions;

public class EmptyNameOfTheTest extends RuntimeException {
    public EmptyNameOfTheTest() {
        System.out.println("Error! Empty name of the test");
    }
}
