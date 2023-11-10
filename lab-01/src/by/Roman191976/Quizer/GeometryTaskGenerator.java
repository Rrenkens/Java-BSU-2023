package by.Roman191976.Quizer;

import java.util.Random;

public class GeometryTaskGenerator implements TaskGenerator {
    private int minNumber;
    private int maxNumber;
    private Random random;

    public GeometryTaskGenerator(int minNumber, int maxNumber) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        random = new Random();
    }

    @Override
    public Task generate() {
        int length = generateRandomNumberBiggerZero(); 
        int width = generateRandomNumberBiggerZero(); 

        int square = length * width;

        String text = "Найдите площадь прямоугольника со сторонами " + length + " и " + width + ".";
        return new GeometryTask(text, square);
    }


    private int generateRandomNumberBiggerZero() {
        int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        return Math.abs(number == 0 ? number + 1 : number);
    }
}