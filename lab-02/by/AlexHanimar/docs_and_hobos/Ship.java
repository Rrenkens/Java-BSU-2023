package by.AlexHanimar.docs_and_hobos;

import by.AlexHanimar.docs_and_hobos.Product;

public class Ship {
    private final String name;
    private final Product product;

    Ship(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public Product getProduct() {
        return product;
    }
}
