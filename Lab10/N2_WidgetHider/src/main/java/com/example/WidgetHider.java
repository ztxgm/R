package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WidgetHider extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Пример текста");
        Slider slider = new Slider();
        TextArea textArea = new TextArea("Введите текст...");

        CheckBox check1 = new CheckBox("Показать текст");
        CheckBox check2 = new CheckBox("Показать слайдер");
        CheckBox check3 = new CheckBox("Показать область текста");

        check1.setSelected(true);
        check2.setSelected(true);
        check3.setSelected(true);

        check1.setOnAction(e -> label.setVisible(check1.isSelected()));
        check2.setOnAction(e -> slider.setVisible(check2.isSelected()));
        check3.setOnAction(e -> textArea.setVisible(check3.isSelected()));

        VBox root = new VBox(10, check1, label, check2, slider, check3, textArea);
        root.setStyle("-fx-padding: 20;");

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Widget Hider");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}