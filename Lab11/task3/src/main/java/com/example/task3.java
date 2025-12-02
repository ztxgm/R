package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class task3 extends Application {
    private ProgressBar progressBar;
    private Button startButton;
    private Button pauseButton;
    private Button stopButton;

    private WorkerThread workerThread;
    private volatile boolean isPaused = false;
    private volatile boolean isStopped = false;
    private final Object pauseLock = new Object();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Пргресирующий БАр");

        // a) Создайте окно с кнопкой «Старт» и пустым ProgressBar
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);

        startButton = new Button("Старт");
        pauseButton = new Button("Пауза");
        stopButton = new Button("Стоп");

        pauseButton.setDisable(true);
        stopButton.setDisable(true);

        // Обработчики кнопок
        startButton.setOnAction(e -> startThread());
        pauseButton.setOnAction(e -> togglePause());
        stopButton.setOnAction(e -> stopThread());

        VBox root = new VBox(10);
        root.getChildren().addAll(progressBar, startButton, pauseButton, stopButton);

        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startThread() {
        // e) При повторном нажатии на кнопку «Старт» поток должен перезапускаться
        if (workerThread != null && workerThread.isAlive()) {
            // Останавливаем старый поток
            isStopped = true;
            isPaused = false;
            synchronized (pauseLock) {
                pauseLock.notifyAll(); // Разбудим поток, если он в wait()
            }
            workerThread.interrupt();
        }

        // Сбрасываем флаги
        isPaused = false;
        isStopped = false;

        // i) ProgressBar очищается до первоначального состояния
        progressBar.setProgress(0);

        // Создаем и запускаем новый поток
        workerThread = new WorkerThread();
        workerThread.start();

        // Обновляем состояние кнопок
        startButton.setDisable(true);
        pauseButton.setDisable(false);
        stopButton.setDisable(false);
        pauseButton.setText("Пауза");
    }

    private void togglePause() {
        if (workerThread == null || !workerThread.isAlive()) return;

        isPaused = !isPaused;
        
        if (isPaused) {
            // g) При нажатии на «Паузу» поток должен остановиться на текущей итерации
            pauseButton.setText("Продолжить");
        } else {
            // h) При нажатии на «Продолжить» поток должен продолжать выполнение
            pauseButton.setText("Пауза");
            synchronized (pauseLock) {
                pauseLock.notify(); // j) Используем notify для пробуждения потока
            }
        }
    }

    private void stopThread() {
        // i) При нажатии на «Стоп» поток завершается, ProgressBar очищается
        if (workerThread != null) {
            isStopped = true;
            isPaused = false;
            
            synchronized (pauseLock) {
                pauseLock.notifyAll(); // Разбудим поток, если он в wait()
            }
            
            workerThread.interrupt();
            
            // Обновляем GUI
            Platform.runLater(() -> {
                progressBar.setProgress(0);
                startButton.setDisable(false);
                pauseButton.setDisable(true);
                stopButton.setDisable(true);
                pauseButton.setText("Пауза");
            });
        }
    }

    // Внутренний класс потока
    class WorkerThread extends Thread {
        private int currentIteration = 0;
        private final int TOTAL_ITERATIONS = 1000;

        @Override
        public void run() {
            try {
                // b) Цикл на 1000 итераций, каждая итерация длится 20 миллисекунд
                for (currentIteration = 0; currentIteration < TOTAL_ITERATIONS; currentIteration++) {
                    // Проверяем остановку
                    if (isStopped || Thread.currentThread().isInterrupted()) {
                        break;
                    }

                    // g) Обработка паузы с использованием wait()
                    if (isPaused) {
                        synchronized (pauseLock) {
                            // j) Используем wait для приостановки потока
                            while (isPaused && !isStopped) {
                                pauseLock.wait(); // Поток ждет пока его разбудят через notify
                            }
                        }
                    }

                    // Проверяем снова после паузы
                    if (isStopped || Thread.currentThread().isInterrupted()) {
                        break;
                    }

                    // b) Каждая итерация длится 20 миллисекунд
                    Thread.sleep(20);

                    // c) ProgressBar отображает текущий результат работы цикла
                    // d) Используем Platform.runLater для передачи информации от потока к GUI
                    final double progress = (double) (currentIteration + 1) / TOTAL_ITERATIONS;
                    Platform.runLater(() -> {
                        progressBar.setProgress(progress);
                    });
                }

                // Завершение работы (только если не была остановка)
                if (!isStopped && !Thread.currentThread().isInterrupted()) {
                    Platform.runLater(() -> {
                        progressBar.setProgress(1.0);
                        startButton.setDisable(false);
                        pauseButton.setDisable(true);
                        stopButton.setDisable(true);
                        pauseButton.setText("Пауза");
                    });
                }

            } catch (InterruptedException e) {
                // Поток был прерван - нормальное завершение
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}