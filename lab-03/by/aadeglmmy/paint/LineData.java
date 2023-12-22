package by.aadeglmmy.paint;

import java.io.Serializable;
import javafx.scene.paint.Color;

public class LineData implements Serializable {

  private final double startX;
  private final double startY;
  private final double endX;
  private final double endY;
  private final int colorRed;
  private final int colorGreen;
  private final int colorBlue;
  private final double width;

  public LineData(double startX, double startY, double endX, double endY, Color color, double width,
      DrawingData drawing) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.colorRed = (int) (color.getRed() * 255);
    this.colorGreen = (int) (color.getGreen() * 255);
    this.colorBlue = (int) (color.getBlue() * 255);
    this.width = width;
    drawing.addLine(this);
  }

  public double getStartX() {
    return startX;
  }

  public double getStartY() {
    return startY;
  }

  public double getEndX() {
    return endX;
  }

  public double getEndY() {
    return endY;
  }

  public Color getColor() {
    return Color.rgb(colorRed, colorGreen, colorBlue);
  }

  public double getWidth() {
    return width;
  }
}
