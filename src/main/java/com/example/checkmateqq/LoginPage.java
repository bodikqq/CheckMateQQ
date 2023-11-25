package com.example.checkmateqq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends Application {
    private MainScene mainScene;
    private Scene loginScene;

    // ... other fields and methods ...

    public void setMainScene(MainScene mainScene) {
        this.mainScene = mainScene;
    }
    public void start(Stage stage) throws IOException {
        LoginController lc = new LoginController();
        FXMLLoader fxmlLoader1 = new FXMLLoader(LoginPage.class.getResource("LoginPage.fxml"));

        // Load the FXML file and set the controller for the first scene
        Scene scene1 = new Scene(fxmlLoader1.load(), 700, 400);
        fxmlLoader1.setController(lc);

        // Create the login scene using the same fxmlLoader1 instance
        loginScene = new Scene(fxmlLoader1.load(), 700, 400);

        stage.setTitle("Hello!");
        stage.setScene(scene1);
        stage.show();
    }
    public Scene getScene() {
        return loginScene;
    }
    public static void main(String[] args) {
        launch();
    }
}