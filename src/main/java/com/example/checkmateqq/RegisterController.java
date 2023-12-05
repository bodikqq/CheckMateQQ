package com.example.checkmateqq;


import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import com.example.checkmateqq.triedy.User;
import java.io.IOException;

public class RegisterController {



    static private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField workerCode;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    @FXML
    private Text qq;
    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField username;

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (Pattern.matches("[^0-9]*", newText)) {
            return change; // Accept the change
        } else {
            return null; // Reject the change
        }
    };

    public void initialize() {
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        firstName.setTextFormatter(textFormatter);

        TextFormatter<String> lastNameFormatter = new TextFormatter<>(filter);
        lastName.setTextFormatter(lastNameFormatter);
    }
    @FXML
    void register(ActionEvent event) throws EntityNotFoundException {
        if(firstName.getText().length() <= 1){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("First name must contain at least 2 characters");
            alert.show();
            return;
        }
        if(lastName.getText().length() <= 1){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Last name must contain at least 2 characters");
            alert.show();
            return;
        }
        if(password.getText().length() < 6){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password must contain at least 6 characters");
            alert.show();
            return;
        }
        if(!password.getText().equals(confirmPassword.getText())){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Passwords are not the same");
            alert.show();
            return;
        }
        if(userDao.checkIfLoginExist(username.getText())){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("this login already exist, please choose another one");
            alert.show();
            return;
        }
        if(workerCode.getText().length() != 0){

            System.out.println("qqq");
        }
        User user = new User(firstName.getText(),lastName.getText(),username.getText(),password.getText(),false,false);
        userDao.save(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Successes, please log in now");
        alert.show();

        LoginController loginController = new LoginController();
        Stage stage = (Stage) registerButton.getScene().getWindow();
        goToLogin(stage, loginController);
    }
    @FXML
    void showCodeField(MouseEvent event) {
        workerCode.setVisible(true);
        qq.setVisible(false);

    }
    @FXML
    void showLoginStage(MouseEvent event) {
        // Create an instance of the LoginController
        LoginController loginController = new LoginController();

        // Call the goToLogin method with the current stage and the LoginController
        Stage stage = (Stage) registerButton.getScene().getWindow();
        goToLogin(stage, loginController);
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
