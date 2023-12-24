package by.LEXUS_FAMCS.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class DrawingArea extends StackPane {
    private final Canvas drawCanvas;
    private final GraphicsContext gcDraw;
    private final Canvas tempCanvas;
    private final GraphicsContext gcTemp;
    private final Canvas borderCanvas;
    private final GraphicsContext gcBorder;
    DrawingArea(DataModel dataModel) {
            drawCanvas = new Canvas();
            gcDraw = drawCanvas.getGraphicsContext2D();
            gcDraw.setLineCap(StrokeLineCap.ROUND);
            gcDraw.setLineJoin(StrokeLineJoin.ROUND);
            drawCanvas.setWidth(700);
            drawCanvas.setHeight(400);
//
            tempCanvas = new Canvas();
            gcTemp = tempCanvas.getGraphicsContext2D();
            tempCanvas.setWidth(drawCanvas.getWidth());
            tempCanvas.setHeight(drawCanvas.getHeight());

            borderCanvas = new Canvas(drawCanvas.getWidth(), drawCanvas.getHeight());
            gcBorder = borderCanvas.getGraphicsContext2D();
            gcBorder.setStroke(Color.BLACK);
            gcBorder.setLineWidth(2);
            gcBorder.strokeRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

            getChildren().addAll(drawCanvas, borderCanvas, tempCanvas);
            //TODO
            Loader loader = new Loader(drawCanvas, dataModel);
            Saver saver = new Saver(drawCanvas, dataModel);

            dataModel.loader = loader;
            dataModel.saver = saver;
            dataModel.drawCanvas = drawCanvas;
            dataModel.tempCanvas = tempCanvas;
            dataModel.borderCanvas = borderCanvas;
            dataModel.gcDraw = gcDraw;
            dataModel.gcTemp = gcTemp;
            dataModel.gcBorder = gcBorder;
    }
}
