package com.example.checkmateqq;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScene extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginPage.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Attender");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
