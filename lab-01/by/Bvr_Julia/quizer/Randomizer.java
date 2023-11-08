package by.Bvr_Julia.quizer;

public class Randomizer {
    public static int generate(int num2, int num1)
    {
        int tmp = num1 - num2;
        return (int) (Math.random() * ++tmp) + num2;
    }
}
