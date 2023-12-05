package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

    @FXML
    void login(ActionEvent event) throws EntityNotFoundException{
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        System.out.println("ahoj");

        if (userDao.checkIfUserExists(enteredUsername, enteredPassword)) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Login successfull");
//            alert.show();
            openKlientScene();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Wrong login or password");
            alert.show();
        }
    }

    @FXML
    void showRegisterStage(MouseEvent event) {
        RegisterController rc = new RegisterController();
        goToRegistration(rc);
    }
    private void goToRegistration(RegisterController controller) {
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
    private void openKlientScene() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("KlientView.fxml"));
            KlientViewController klientController = new KlientViewController();
            loader.setController(klientController);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Klient View");
            stage.show();

            // Zatvorenie aktuálnej scény (prihlasovacej)
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
