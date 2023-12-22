package by.bvr_julia.paint;

import javafx.scene.paint.Color;

public class Brush {
    private Color color = Color.BLACK;
    private Integer size = 5;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
