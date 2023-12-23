package by.aadeglmmy.paint;

import java.io.Serializable;
import javafx.scene.paint.Color;

public class OvalData implements Serializable {

  private final double x;
  private final double y;
  private final double width;
  private final double height;
  private final int colorRed;
  private final int colorGreen;
  private final int colorBlue;
  private final double brushWidth;

  public OvalData(double x, double y, double width, double height, Color color, double brushWidth,
      DrawingData drawing) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.colorRed = (int) (color.getRed() * 255);
    this.colorGreen = (int) (color.getGreen() * 255);
    this.colorBlue = (int) (color.getBlue() * 255);
    this.brushWidth = brushWidth;
    drawing.addOval(this);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public Color getColor() {
    return Color.rgb(colorRed, colorGreen, colorBlue);
  }

  public double getBrushWidth() {
    return brushWidth;
  }
}
