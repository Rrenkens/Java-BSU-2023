package by.Bvr_Julia.docks_and_hobos;

public class Randomizer {
    public static Long generate(Long num2, Long num1) {
        int tmp = num1.intValue() - num2.intValue();
        return (long) (Math.random() * ++tmp) + num2.intValue();
    }
}