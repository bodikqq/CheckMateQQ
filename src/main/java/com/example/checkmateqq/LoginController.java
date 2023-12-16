package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    private User user = null;

    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

    public void initialize() {
        loginButton.setDefaultButton(true);
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    password.requestFocus();
                }
            }

        });
        loginButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("qqqq");
                    loginButton.fire();
                }
            }
        });

        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN) {
                    password.requestFocus();
                }
            }

        });

        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    username.requestFocus();
                }
            }
        });
    }

    @FXML
    void login(ActionEvent event) throws EntityNotFoundException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        System.out.println("ahoj");

        User user = userDao.getUserByLoginAndPassword(enteredUsername, enteredPassword);
        this.user = user;
        if (user != null && user.isEmployee()) {
            if(user.isAdmin()){
                openAdminScene();
            }else {
                openWorkerScene();
            }
        } else if (user != null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Login successfull");
//            alert.show();
            openKlientScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Wrong login or password");

            // Create a custom style class for the alert
            alert.getDialogPane().getStyleClass().add("bootstrap-alert");

            // Set the alert size to fit content
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            //  alert.getStyle;
        }

    }


        @FXML
        void showRegisterStage (MouseEvent event){
            RegisterController rc = new RegisterController();
            goToRegistration(rc);
        }
        private void goToRegistration (RegisterController controller){
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
        private void openKlientScene () {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("KlientView.fxml"));
                KlientViewController klientController = new KlientViewController();
                klientController.setUserId(user);
                loader.setController(klientController);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Client View");
                stage.show();

                // Zatvorenie aktuálnej scény (prihlasovacej)
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void openWorkerScene () {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkerView.fxml"));
                WorkerViewController workerController = new WorkerViewController();
                workerController.setUserId(user);
                loader.setController(workerController);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Worker View");
                stage.show();

                // Zatvorenie aktuálnej scény (prihlasovacej)
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    private void openAdminScene () {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
            AdminViewController adminController = new AdminViewController();
            adminController.setUserId(user);
            loader.setController(adminController);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Admin View");
            stage.show();

            // Zatvorenie aktuálnej scény (prihlasovacej)
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }



