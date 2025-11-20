package com.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFlag extends Application {
    private ToggleGroup group1 = new ToggleGroup();
    private ToggleGroup group2 = new ToggleGroup();
    private ToggleGroup group3 = new ToggleGroup();

    @Override
    public void start(Stage primaryStage) {
        String[] colors = {"Красный", "Синий", "Зеленый", "Желтый", "Белый"};

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        root.getChildren().add(new Label("Выберите цвета полос флага:"));
        root.getChildren().add(createRadioGroup("Верхняя полоса", colors, group1));
        root.getChildren().add(createRadioGroup("Средняя полоса", colors, group2));
        root.getChildren().add(createRadioGroup("Нижняя полоса", colors, group3));

        Button drawBtn = new Button("Нарисовать");
        Label resultLabel = new Label();

        drawBtn.setOnAction(e -> {
            String top = getSelectedColor(group1);
            String middle = getSelectedColor(group2);
            String bottom = getSelectedColor(group3);
            resultLabel.setText(top + ", " + middle + ", " + bottom);
        });

        root.getChildren().addAll(drawBtn, resultLabel);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text Flag");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createRadioGroup(String label, String[] colors, ToggleGroup group) {
        HBox box = new HBox(10);
        box.getChildren().add(new Label(label + ":"));
        for (String color : colors) {
            RadioButton rb = new RadioButton(color);
            rb.setToggleGroup(group);
            box.getChildren().add(rb);
        }
        ((RadioButton) box.getChildren().get(1)).setSelected(true);
        return box;
    }

    private String getSelectedColor(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return selected != null ? selected.getText() : "Не выбрано";
    }

    public static void main(String[] args) {
        launch(args);
    }
}