package by.aadeglmmy.paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawingData implements Serializable {

  private final List<LineData> lines;
  private final List<OvalData> ovals;
  private final List<RectangleData> rectangles;

  public DrawingData() {
    lines = new ArrayList<>();
    ovals = new ArrayList<>();
    rectangles = new ArrayList<>();
  }

  public void addLine(LineData lineData) {
    lines.add(lineData);
  }

  public void addOval(OvalData ovalData) {
    ovals.add(ovalData);
  }

  public void addRectangle(RectangleData rectangleData) {
    rectangles.add(rectangleData);
  }

  public List<LineData> getLines() {
    return lines;
  }

  public List<OvalData> getOvals() {
    return ovals;
  }

  public List<RectangleData> getRectangles() {
    return rectangles;
  }
}
