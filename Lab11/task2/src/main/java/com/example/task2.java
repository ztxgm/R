package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class task2 extends Application {
    private Button blockingButton;
    private Button runnableButton;
    private Button threadButton;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Потоки");

        blockingButton = new Button("Демонстрация зависания (пункт a)");
        runnableButton = new Button("Запуск через Runnable (пункт b)");
        threadButton = new Button("Запуск через Thread (пункт b)");
        statusLabel = new Label("Статус: Готов");

        // a) зависание - окно должно перестать отвечать при надатии на кнопку, ну надеюсь
        blockingButton.setOnAction(e -> {
            statusLabel.setText("Статус: ЗАВИСАНИЕ - окно не отвечает!");
            
            // беск цикл в GUI
            while (true) {
                // Просто бесконечный цикл без выхода
                double x = Math.random();
                
                // Попытка обновить статус (не сработает после первой итерации)
                if (Math.random() < 0.0001) {
                    // Этот код никогда не выполнится из-за блокировки GUI
                    statusLabel.setText("Счет: " + x);
                }
            }
        });

        // b1) через Runnable - без зависания
        runnableButton.setOnAction(e -> {
            statusLabel.setText("Запуск через Runnable...");
            
            Runnable task = () -> {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100); // 0.1 секунда задержка
                        final int current = i;
                        
                        // Правильное обновление GUI из отдельного потока
                        javafx.application.Platform.runLater(() -> 
                            statusLabel.setText("Runnable: итерация " + (current + 1) + "/100")
                        );
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
                
                javafx.application.Platform.runLater(() -> 
                    statusLabel.setText("Runnable завершен")
                );
            };
            
            // Запуск в отдельном потоке
            new Thread(task).start();
        });

        // b2) вариант через наследование от класса Thread - без зависания
        threadButton.setOnAction(e -> {
            statusLabel.setText("Запуск через Thread...");
            
            class MyThread extends Thread {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        try {
                            Thread.sleep(100); // 0.1 секунда задержка
                            final int current = i;
                            
                            // Правильное обновление GUI из отдельного потока
                            javafx.application.Platform.runLater(() -> 
                                statusLabel.setText("Thread: итерация " + (current + 1) + "/100")
                            );
                        } catch (InterruptedException ex) {
                            break;
                        }
                    }
                    
                    javafx.application.Platform.runLater(() -> 
                        statusLabel.setText("Thread завершен")
                    );
                }
            }
            
            // Запуск в отдельном потоке
            new MyThread().start();
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(statusLabel, blockingButton, runnableButton, threadButton);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}