package com.example.checkmateqq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class KlientViewController {
    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<?> station;

    @FXML
    private TableColumn<?, ?> testTable;

    @FXML
    private Button selectTerm;
    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) selectTerm.getScene().getWindow();
        goToLogin(stage, loginController);
    }
    @FXML
    void pickDate(ActionEvent event) {

    }

    @FXML
    void saveTestTerm(ActionEvent event) {

    }
    private void goToLogin(Stage currentStage, LoginController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("LoginPage.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();

            // Set the new content in the current stage
            Scene scene = new Scene(parent);
            currentStage.setScene(scene);

            // Set the title (if needed)
            currentStage.setTitle("qq");

            // Optionally, show the stage (if it's not already showing)
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
