package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {
    private TextField display = new TextField();
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        display.setEditable(false);
        display.setStyle("-fx-font-size: 18;");

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        int col = 0, row = 0;
        for (String text : buttons) {
            Button button = new Button(text);
            button.setPrefSize(50, 50);
            button.setStyle("-fx-font-size: 16;");
            button.setOnAction(e -> handleButton(text));

            grid.add(button, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        Button clearBtn = new Button("C");
        clearBtn.setPrefSize(50, 50);
        clearBtn.setStyle("-fx-font-size: 16;");
        clearBtn.setOnAction(e -> {
            display.clear();
            operator = "";
            startNewNumber = true;
        });

        grid.add(clearBtn, 4, 0);

        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(5);
        root.add(display, 0, 0, 5, 1);
        root.add(grid, 0, 1, 5, 1);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleButton(String value) {
        if ("0123456789.".contains(value)) {
            if (startNewNumber) {
                display.clear();
                startNewNumber = false;
            }
            // Проверка на множественные точки
            if (value.equals(".") && display.getText().contains(".")) {
                return;
            }
            display.appendText(value);
        } else if ("+-*/".contains(value)) {
            if (!display.getText().isEmpty()) {
                firstNumber = Double.parseDouble(display.getText());
                operator = value;
                startNewNumber = true;
            }
        } else if ("=".equals(value)) {
            if (!operator.isEmpty() && !display.getText().isEmpty()) {
                double secondNumber = Double.parseDouble(display.getText());
                double result = calculate(firstNumber, secondNumber, operator);
                if (!Double.isNaN(result)) {
                    display.setText(String.valueOf(result));
                }
                operator = "";
                startNewNumber = true;
            }
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": 
                return a + b;
            case "-": 
                return a - b;
            case "*": 
                return a * b;
            case "/": 
                if (b == 0) {
                    display.setText("Ошибка: деление на 0");
                    startNewNumber = true;
                    operator = "";
                    return Double.NaN;
                }
                return a / b;
            default: 
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}