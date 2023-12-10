package by.AlexHanimar.docs_and_hobos;

public class Product {
    private int count;
    final private String name;

    Product(String name, int count) {
        this.count = count;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    synchronized public void SubCount(int value) {
        count -= value;
    }

    synchronized public void AddCount(int value) {
        count += value;
    }
}
