import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomColorPicker extends HBox {
    public Color getMainColor() {
        return mainColor;
    }

    public Color getExtraColor() {
        return extraColor;
    }

    private Color mainColor = Color.BLACK;
    private Color extraColor = Color.WHITE;
    private final Dialog<Color> colorDialog;

    CustomColorPicker() {
        setSpacing(8);

        colorDialog = new Dialog<>();
        colorDialog.setTitle("Выбор цвета");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        colorDialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        ColorPicker colorPicker = new ColorPicker();
        colorDialog.getDialogPane().setContent(colorPicker);

        colorDialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                return colorPicker.getValue();
            }
            return null;
        });

        Button mainColorButton = new Button();
        Button extraColorButton = new Button();

        mainColorButton.setOnAction(e -> {
            colorDialog.showAndWait().ifPresent(result -> {
                mainColor = result;
                mainColorButton.setStyle(
                        "-fx-border-color: black;" +      // чёрная граница
                                "-fx-border-width: 1px;" +        // толщина границы
                                "-fx-background-color: " + colorToHex(result) + ";" +    // красный фоновый цвет, белая прослойка
                                "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
                );
            });
        });
        extraColorButton.setOnAction(e -> {
                colorDialog.showAndWait().ifPresent(result -> {
                    extraColor = result;
                    extraColorButton.setStyle(
                            "-fx-border-color: black;" +      // чёрная граница
                                    "-fx-border-width: 1px;" +        // толщина границы
                                    "-fx-background-color: " + colorToHex(result) + ";" +    // красный фоновый цвет, белая прослойка
                                    "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
                    );
                });
        });

        mainColorButton.setPrefSize(30, 30);
        extraColorButton.setPrefSize(mainColorButton.getPrefWidth(), mainColorButton.getPrefHeight());

        mainColorButton.setStyle(
                "-fx-border-color: black;" +      // чёрная граница
                        "-fx-border-width: 1px;" +        // толщина границы
                        "-fx-background-color: black;" +    // красный фоновый цвет, белая прослойка
                        "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
        );
        extraColorButton.setStyle(
                "-fx-border-color: black;" +      // чёрная граница
                        "-fx-border-width: 1px;" +        // толщина границы
                        "-fx-background-color: white;" +    // красный фоновый цвет, белая прослойка
                        "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
        );

        AnchorPane.setTopAnchor(mainColorButton, 0.0);
        AnchorPane.setLeftAnchor(mainColorButton, 0.0);
        AnchorPane.setRightAnchor(extraColorButton, 0.0);
        AnchorPane.setBottomAnchor(extraColorButton, 0.0);

        AnchorPane curr_colors = new AnchorPane();
        curr_colors.setPrefSize(50, 50);
        curr_colors.getChildren().addAll(extraColorButton, mainColorButton);

        List<List<String>> rowColors = new ArrayList<>();
        rowColors.add(List.of("#FFFFFF", "#A0A0A0", "#FF0000", "#FF6A00",
                                        "#FFD800", "#4CFF00", "#00FF90", "#00FFFF",
                                        "#0026FF", "#4800FF", "#B200FF"));
        rowColors.add(List.of("#000000", "#808080", "#FF7F7F", "#FFB27F",
                                        "#FFE97F", "#A5FF7F", "#7FFFC5", "#7FFFFF",
                                        "#7F92FF", "#A17FFF", "#D67FFF"));

        GridPane colors = new GridPane();
        for (int j = 0, s = rowColors.size(); j < s; ++j) {
            var rowColor = rowColors.get(j);
            for (int i = 0, size = rowColor.size(); i < size; ++i) {
                Button btn = new Button();
                int finalI = i;
                btn.setOnMouseClicked(e -> {
                    var type = e.getButton();
                    if (type == MouseButton.PRIMARY) {
                        mainColor = Color.web(rowColor.get(finalI));
                        mainColorButton.setStyle(
                                "-fx-border-color: black;" +      // чёрная граница
                                        "-fx-border-width: 1px;" +        // толщина границы
                                        "-fx-background-color: " + rowColor.get(finalI) + ";" +    // красный фоновый цвет, белая прослойка
                                        "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
                        );
                    } else if (type == MouseButton.SECONDARY) {
                        extraColor = Color.web(rowColor.get(finalI));
                        extraColorButton.setStyle(
                                "-fx-border-color: black;" +      // чёрная граница
                                        "-fx-border-width: 1px;" +        // толщина границы
                                        "-fx-background-color: " + rowColor.get(finalI) + ";" +    // красный фоновый цвет, белая прослойка
                                        "-fx-background-insets: 2;"  // отступы для фона (белая прослойка между границей и фоном)
                        );
                    }
                });
                colors.add(btn, i, j);
                btn.setPrefSize(25, 25);
                btn.setBackground(Background.fill(Color.web(rowColor.get(i))));
            }
        }


        getChildren().addAll(curr_colors, colors);
    }

    private String colorToHex(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);

        return String.format("rgb(%d, %d, %d)", red, green, blue);
    }
}
