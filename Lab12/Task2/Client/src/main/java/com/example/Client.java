import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private ProgressBar progressBar;
    private Button startButton;
    private Button pauseResumeButton;
    private Button stopButton;
    private Label statusLabel;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private volatile boolean isPaused = false;
    private Thread serverListener;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Прогресс Бар Клиент");
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        
        startButton = new Button("Старт");
        pauseResumeButton = new Button("Пауза");
        stopButton = new Button("Стоп");
        
        pauseResumeButton.setDisable(true);
        stopButton.setDisable(true);
        
        statusLabel = new Label("Статус: Ожидание");
        
        startButton.setOnAction(e -> startProgress());
        pauseResumeButton.setOnAction(e -> togglePauseResume());
        stopButton.setOnAction(e -> stopProgress());
        
        HBox buttonBox = new HBox(10, startButton, pauseResumeButton, stopButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(20, progressBar, buttonBox, statusLabel);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        connectToServer();
        
        primaryStage.setOnCloseRequest(e -> disconnectFromServer());
    }
    
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 5555);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            serverListener = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        handleServerMessage(serverMessage);
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> 
                        statusLabel.setText("Статус: Соединение разорвано"));
                }
            });
            serverListener.setDaemon(true);
            serverListener.start();
            
            Platform.runLater(() -> 
                statusLabel.setText("Статус: Подключен к серверу"));
            
        } catch (IOException e) {
            Platform.runLater(() -> 
                statusLabel.setText("Статус: Ошибка подключения"));
            e.printStackTrace();
        }
    }
    
    private void handleServerMessage(String message) {
        Platform.runLater(() -> {
            if (message.startsWith("PROGRESS:")) {
                try {
                    int progressValue = Integer.parseInt(message.substring(9));
                    double progress = progressValue / 1000.0;
                    progressBar.setProgress(progress);
                    statusLabel.setText("Прогресс: " + progressValue + "/1000");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (message.equals("FINISHED")) {
                progressBar.setProgress(1.0);
                startButton.setDisable(false);
                pauseResumeButton.setDisable(true);
                stopButton.setDisable(true);
                statusLabel.setText("Статус: Завершено");
            } else if (message.equals("STOPPED")) {
                progressBar.setProgress(0);
                startButton.setDisable(false);
                pauseResumeButton.setDisable(true);
                stopButton.setDisable(true);
                isPaused = false;
                pauseResumeButton.setText("Пауза");
                statusLabel.setText("Статус: Остановлено");
            }
        });
    }
    
    private void startProgress() {
        if (out != null) {
            out.println("START");
            startButton.setDisable(true);
            pauseResumeButton.setDisable(false);
            stopButton.setDisable(false);
            isPaused = false;
            pauseResumeButton.setText("Пауза");
            statusLabel.setText("Статус: Запущено");
        }
    }
    
    private void togglePauseResume() {
        if (out != null) {
            if (isPaused) {
                out.println("RESUME");
                pauseResumeButton.setText("Пауза");
                statusLabel.setText("Статус: Продолжено");
            } else {
                out.println("PAUSE");
                pauseResumeButton.setText("Продолжить");
                statusLabel.setText("Статус: На паузе");
            }
            isPaused = !isPaused;
        }
    }
    
    private void stopProgress() {
        if (out != null) {
            out.println("STOP");
            startButton.setDisable(false);
            pauseResumeButton.setDisable(true);
            stopButton.setDisable(true);
            isPaused = false;
            pauseResumeButton.setText("Пауза");
            statusLabel.setText("Статус: Остановка...");
        }
    }
    
    private void disconnectFromServer() {
        try {
            if (out != null) {
                out.println("STOP");
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}