import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    private int playerNumber;
    private boolean myTurn = false;
    private int remainingMatches = 37;
    
    // Элементы интерфейса
    private Label playerLabel;
    private Label statusLabel;
    private Label matchesLabel;
    private TextField inputField;
    private Button takeButton;
    private Button[] matchButtons;
    private VBox root;
    private HBox matchesBox;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Игра: Последняя спичка");
        
        // Создание интерфейса
        createUI();
        
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Подключение к серверу
        connectToServer();
        
        // Обработка закрытия окна
        primaryStage.setOnCloseRequest(e -> disconnect());
    }
    
    private void createUI() {
        // Заголовок
        Label titleLabel = new Label("ПОСЛЕДНЯЯ СПИЧКА");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Информация о игроке
        playerLabel = new Label("Ожидание подключения...");
        playerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Статус игры
        statusLabel = new Label("Подключаемся к серверу...");
        statusLabel.setFont(Font.font("Arial", 14));
        
        // Отображение спичек
        matchesLabel = new Label("Спичек: 37");
        matchesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Графическое отображение спичек
        matchesBox = new HBox(5);
        matchesBox.setAlignment(Pos.CENTER);
        updateMatchesDisplay();
        
        // Поле ввода и кнопки
        inputField = new TextField();
        inputField.setPromptText("Введите число от 1 до 5");
        inputField.setPrefWidth(100);
        inputField.setDisable(true);
        
        takeButton = new Button("Взять спички");
        takeButton.setDisable(true);
        takeButton.setOnAction(e -> takeMatches());
        
        // Кнопки для быстрого выбора
        HBox quickButtonsBox = new HBox(10);
        quickButtonsBox.setAlignment(Pos.CENTER);
        matchButtons = new Button[5];
        
        for (int i = 0; i < 5; i++) {
            final int count = i + 1;
            matchButtons[i] = new Button(String.valueOf(count));
            matchButtons[i].setPrefSize(50, 40);
            matchButtons[i].setFont(Font.font("Arial", FontWeight.BOLD, 16));
            matchButtons[i].setDisable(true);
            matchButtons[i].setOnAction(e -> {
                inputField.setText(String.valueOf(count));
                takeMatches();
            });
            quickButtonsBox.getChildren().add(matchButtons[i]);
        }
        
        // Сборка интерфейса
        VBox inputBox = new VBox(10, 
            new Label("Сколько спичек взять?"),
            inputField,
            takeButton,
            new Label("Или нажмите:"),
            quickButtonsBox
        );
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(20));
        
        root = new VBox(20,
            titleLabel,
            playerLabel,
            statusLabel,
            matchesLabel,
            matchesBox,
            inputBox
        );
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #e6f7ff);");
    }
    
    private void updateMatchesDisplay() {
        matchesBox.getChildren().clear();
        
        // Отображаем спички как прямоугольники
        int matchesToShow = Math.min(remainingMatches, 30); // Не показываем все 37, если много
        
        for (int i = 0; i < matchesToShow; i++) {
            Pane match = new Pane();
            match.setPrefSize(10, 30);
            match.setStyle("-fx-background-color: brown; -fx-border-color: #8B4513; -fx-border-width: 1;");
            matchesBox.getChildren().add(match);
        }
        
        if (remainingMatches > 30) {
            Label moreLabel = new Label("+ " + (remainingMatches - 30) + " еще");
            moreLabel.setFont(Font.font("Arial", 12));
            matchesBox.getChildren().add(moreLabel);
        }
    }
    
    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5555);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                // Поток для прослушивания сообщений от сервера
                new Thread(() -> {
                    try {
                        String serverMessage;
                        while ((serverMessage = in.readLine()) != null) {
                            handleServerMessage(serverMessage);
                        }
                    } catch (IOException e) {
                        Platform.runLater(() -> 
                            statusLabel.setText("Соединение с сервером потеряно"));
                    }
                }).start();
                
            } catch (IOException e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Не удалось подключиться к серверу");
                    statusLabel.setTextFill(Color.RED);
                });
            }
        }).start();
    }
    
    private void handleServerMessage(String message) {
        Platform.runLater(() -> {
            System.out.println("Получено: " + message);
            
            if (message.startsWith("PLAYER_NUMBER:")) {
                playerNumber = Integer.parseInt(message.substring(14));
                playerLabel.setText("Вы - Игрок " + playerNumber);
                playerLabel.setTextFill(playerNumber == 1 ? Color.BLUE : Color.RED);
                
            } else if (message.startsWith("GAME_START:")) {
                remainingMatches = Integer.parseInt(message.substring(11));
                matchesLabel.setText("Спичек: " + remainingMatches);
                statusLabel.setText("Игра началась! Всего спичек: " + remainingMatches);
                updateMatchesDisplay();
                
            } else if (message.startsWith("TURN:")) {
                int turnPlayer = Integer.parseInt(message.substring(5));
                myTurn = (turnPlayer == playerNumber);
                
                if (myTurn) {
                    statusLabel.setText("ВАШ ХОД! Возьмите от 1 до 5 спичек");
                    statusLabel.setTextFill(Color.GREEN);
                    inputField.setDisable(false);
                    takeButton.setDisable(false);
                    for (Button btn : matchButtons) {
                        btn.setDisable(false);
                    }
                } else {
                    statusLabel.setText("Ход игрока " + turnPlayer + ". Ожидайте...");
                    statusLabel.setTextFill(Color.GRAY);
                    inputField.setDisable(true);
                    takeButton.setDisable(true);
                    for (Button btn : matchButtons) {
                        btn.setDisable(true);
                    }
                }
                
            } else if (message.startsWith("UPDATE:")) {
                remainingMatches = Integer.parseInt(message.substring(7));
                matchesLabel.setText("Спичек: " + remainingMatches);
                updateMatchesDisplay();
                
            } else if (message.startsWith("STATUS:")) {
                statusLabel.setText(message.substring(7));
                
            } else if (message.startsWith("ERROR:")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(message.substring(6));
                alert.showAndWait();
                
            } else if (message.startsWith("GAME_OVER:")) {
                statusLabel.setText(message.substring(10));
                statusLabel.setTextFill(Color.DARKRED);
                inputField.setDisable(true);
                takeButton.setDisable(true);
                for (Button btn : matchButtons) {
                    btn.setDisable(true);
                }
                
            } else if (message.startsWith("WINNER:")) {
                int winner = Integer.parseInt(message.substring(7));
                if (winner == playerNumber) {
                    playerLabel.setText("ВЫ ПОБЕДИЛИ! 🏆");
                    playerLabel.setTextFill(Color.GOLD);
                }
                
            } else if (message.startsWith("RESULT:")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат игры");
                alert.setHeaderText(null);
                alert.setContentText(message.substring(7));
                alert.showAndWait();
            }
        });
    }
    
    private void takeMatches() {
        try {
            int matchesToTake = Integer.parseInt(inputField.getText().trim());
            
            if (matchesToTake < 1 || matchesToTake > 5) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Можно брать от 1 до 5 спичек");
                alert.showAndWait();
                return;
            }
            
            if (matchesToTake > remainingMatches) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Нельзя взять больше спичек, чем осталось");
                alert.showAndWait();
                return;
            }
            
            if (out != null) {
                out.println("MOVE:" + matchesToTake);
                inputField.clear();
                inputField.setDisable(true);
                takeButton.setDisable(true);
                for (Button btn : matchButtons) {
                    btn.setDisable(true);
                }
                statusLabel.setText("Ход отправлен...");
            }
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Введите число от 1 до 5");
            alert.showAndWait();
        }
    }
    
    private void disconnect() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}