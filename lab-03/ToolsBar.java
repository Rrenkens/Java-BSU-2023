import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class ToolsBar extends VBox {
    DataModel dataModel;
    private final ToggleGroup instrGroup;
    private final ToggleButton brush;
    private final ToggleButton pencil;
    private final ToggleButton rect;
    private final ToggleButton oval;

    private final List<Integer> indexes;
    ToolsBar(DataModel dataModel) {
        this.dataModel = dataModel;
        setSpacing(10);

        instrGroup = new ToggleGroup();

        brush = new ToggleButton();
        brush.setId("brush");
        brush.fire();
        brush.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        ImageView brushImageView = new ImageView(new Image("/images/Pinta/brush.png"));
        brushImageView.setFitWidth(28);
        brushImageView.setFitHeight(29);
        brush.setGraphic(brushImageView);
        brush.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        pencil = new ToggleButton();
        pencil.setId("pencil");
        ImageView pencilImageView = new ImageView(new Image("/images/Pinta/pencil.png"));
        pencilImageView.setFitWidth(brushImageView.getFitWidth());
        pencilImageView.setFitHeight(brushImageView.getFitHeight());
        pencil.setStyle("-fx-background-color: transparent;");
        pencil.setGraphic(pencilImageView);
        pencil.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        rect = new ToggleButton();
        rect.setId("rect");
        ImageView rectImageView = new ImageView(new Image("/images/Pinta/rect.png"));
        rectImageView.setFitWidth(brushImageView.getFitWidth());
        rectImageView.setFitHeight(brushImageView.getFitHeight());
        rect.setStyle("-fx-background-color: transparent;");
        rect.setGraphic(rectImageView);
        rect.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        oval = new ToggleButton();
        oval.setId("oval");
        ImageView ovalImageView = new ImageView(new Image("/images/Pinta/oval.png"));
        ovalImageView.setFitWidth(30);
        ovalImageView.setFitHeight(30);
        oval.setStyle("-fx-background-color: transparent;");
        oval.setGraphic(ovalImageView);
        oval.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        instrGroup.getToggles().addAll(brush, pencil, rect, oval);
        indexes = List.of(2, 3);
        dataModel.indexes = indexes;

        setEvents();

        getChildren().addAll(brush, pencil, rect, oval);

        dataModel.tools = this;
    }

    private void setEvents() {
        brush.setOnMouseEntered(e -> {
            brush.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        brush.setOnMouseExited(e -> {
            if (!brush.isSelected()) {
                brush.setStyle("-fx-background-color: transparent;");
                brush.setDisable(false);
            };
        });

        pencil.setOnMouseEntered(e -> {
            pencil.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        pencil.setOnMouseExited(e -> {
            if (!pencil.isSelected()) {
                pencil.setStyle("-fx-background-color: transparent;");
            }
        });

        rect.setOnMouseEntered(e -> {
            rect.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        rect.setOnMouseExited(e -> {
            if (!rect.isSelected()) {
                rect.setStyle("-fx-background-color: transparent;");
            }
        });

        oval.setOnMouseEntered(e -> {
            oval.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        oval.setOnMouseExited(e -> {
            if (!oval.isSelected()) {
                oval.setStyle("-fx-background-color: transparent;");
            }
        });

        instrGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                ((ToggleButton) oldValue).setStyle("-fx-background-color: transparent;");
            }
            if (newValue != null) {
                ((ToggleButton) newValue).setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
                dataModel.infoBar.infoSetter(((ToggleButton) newValue).getId());
            } else {
                dataModel.infoBar.infoSetter("");
            }
        });
    }
}
