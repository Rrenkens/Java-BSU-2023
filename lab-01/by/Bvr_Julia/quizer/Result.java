package by.Bvr_Julia.quizer;

/**
 * Enum, which describes the result of the answer to the task
 */
public enum Result {
    OK, // Correct answer received
    WRONG, // Wrong answer received
    INCORRECT_INPUT // Invalid input. For example, text when a number was expected
}