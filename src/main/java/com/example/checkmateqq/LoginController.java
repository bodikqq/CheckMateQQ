package com.example.checkmateqq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void login(ActionEvent event) {
        System.out.print("qq");
    }

    @FXML
    void showRegisterStage(MouseEvent event) {
        RegisterController rc = new RegisterController();
        openSubjectEditWindow(rc);
    }
    private void openSubjectEditWindow(RegisterController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RegisterView.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Set the new content in the current stage
            Scene scene = new Scene(parent);
            stage.setScene(scene);

            // Set the title (if needed)
            stage.setTitle("qq");

            // Optionally, show the stage (if it's not already showing)
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
