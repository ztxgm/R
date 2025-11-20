package com.example;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WordSwitcher extends Application {
    private boolean isForward = true;
    private TextField field1, field2;
    private Button switchButton;

    @Override
    public void start(Stage primaryStage) {
        field1 = new TextField();
        field2 = new TextField();
        field1.setPrefWidth(150);
        field2.setPrefWidth(150);

        switchButton = new Button("→");
        switchButton.setFont(Font.font(20));
        switchButton.setOnAction(e -> switchText());

        HBox root = new HBox(10, field1, switchButton, field2);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Word Switcher");
        primaryStage.show();
    }

    private void switchText() {
        if (isForward) {
            field2.setText(field1.getText());
            field1.clear();
            switchButton.setText("←");
        } else {
            field1.setText(field2.getText());
            field2.clear();
            switchButton.setText("→");
        }
        isForward = !isForward;
    }

    public static void main(String[] args) {
        launch(args);
    }
}