package com.example.checkmateqq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(LoginPage.class.getResource("LoginPage.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 700, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}