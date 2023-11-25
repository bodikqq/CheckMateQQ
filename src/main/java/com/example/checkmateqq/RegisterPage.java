package com.example.checkmateqq;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPage extends Application {
    private MainScene mainScene;
    private Scene registerScene;
    // ... other fields and methods ...

    @Override
    public void start(Stage stage) throws IOException {

        RegisterController rc = new RegisterController();
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterPage.class.getResource("RegisterView.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 700, 400);
        fxmlLoader.setController(rc);
        registerScene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void setMainScene(MainScene mainScene) {
        this.mainScene = mainScene;
    }
    public Scene getScene() {
        return registerScene;
    }
}