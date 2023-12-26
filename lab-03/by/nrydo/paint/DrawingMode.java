package by.nrydo.paint;

public enum DrawingMode  {
    PEN, LINE, ELLIPSE, RECTANGLE, ERASER;

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
