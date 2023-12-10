package by.Katya841.quizer;
import java.util.Random;

public class Rand {
    public static int generateNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        int randomNum = minNumber + rand.nextInt((maxNumber - minNumber) + 1);
        return randomNum;
    }
}
