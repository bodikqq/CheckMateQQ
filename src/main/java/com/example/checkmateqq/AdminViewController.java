package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminViewController {

    @FXML
    private Label UserNameOnTopBar;

    @FXML
    private Text logOutButton;

    @FXML
    private Button openEmpManagerButton;
    private User user;

    @FXML
    void generateEmployeeCode(ActionEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void openEmpManager(ActionEvent event) {
        EmpManagerViewController controller = new EmpManagerViewController();
        Stage stage = (Stage) openEmpManagerButton.getScene().getWindow();
        controller.setUserId(user);
        goToEmpManager(stage, controller);
    }

    public void setUserId(User user) {
        this.user = user;
    }

    private void goToEmpManager(Stage currentStage, EmpManagerViewController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("EmpManagerView.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();

            // Set the new content in the current stage
            Stage searchStage = new Stage();
            searchStage.setTitle("Search For Test");
            searchStage.setScene(new Scene(parent));

            // Add the stylesheet to the scene
            searchStage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            searchStage.initModality(Modality.APPLICATION_MODAL);
            searchStage.initOwner(currentStage);
            searchStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

