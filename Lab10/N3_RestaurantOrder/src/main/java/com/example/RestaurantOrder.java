package com.example;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantOrder extends Application {
    private ObservableList<String> orderItems = FXCollections.observableArrayList();
    private ListView<String> receiptView = new ListView<>();
    private Label totalLabel = new Label("Итого: 0.0");
    private VBox root;

    @Override
    public void start(Stage primaryStage) {
        String[] dishes = {"Суп", "Еще суп", "Третий суп", "Очередной суп"};
        double[] prices = {5.0, 4.5, 12.0, 3.5};

        root = new VBox(10); // Инициализация root
        root.setPadding(new Insets(15));

        for (int i = 0; i < dishes.length; i++) {
            final int index = i;
            CheckBox check = new CheckBox(dishes[i] + " (" + prices[i] + ")");
            Spinner<Integer> spinner = new Spinner<>(0, 10, 0);
            spinner.setDisable(true);

            check.setOnAction(e -> {
                spinner.setDisable(!check.isSelected());
                if (check.isSelected()) spinner.getValueFactory().setValue(1);
                else spinner.getValueFactory().setValue(0);
                updateReceipt(dishes, prices);
            });

            spinner.valueProperty().addListener((obs, oldVal, newVal) -> updateReceipt(dishes, prices));

            HBox row = new HBox(10, check, spinner);
            root.getChildren().add(row);
        }

        receiptView.setPrefHeight(150);
        root.getChildren().addAll(new Label("Чек:"), receiptView, totalLabel);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Restaurant Order");
        primaryStage.show();
    }

    private void updateReceipt(String[] dishes, double[] prices) {
        receiptView.getItems().clear();
        double total = 0;

        for (int i = 0; i < root.getChildren().size() - 3; i++) {
            if (root.getChildren().get(i) instanceof HBox) {
                HBox row = (HBox) root.getChildren().get(i);
                CheckBox check = (CheckBox) row.getChildren().get(0);
                Spinner<Integer> spinner = (Spinner<Integer>) row.getChildren().get(1);

                if (check.isSelected() && spinner.getValue() > 0) {
                    // Извлекаем название блюда из текста CheckBox
                    String dishName = check.getText().split(" \\(")[0];
                    double price = Double.parseDouble(check.getText().split("\\(")[1].replace(")", ""));
                    double cost = price * spinner.getValue();
                    receiptView.getItems().add(dishName + " x " + spinner.getValue() + " = " + cost);
                    total += cost;
                }
            }
        }

        totalLabel.setText("Итого: " + total);
    }

    public static void main(String[] args) {
        launch(args);
    }
}