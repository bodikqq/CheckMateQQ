package com.example.checkmateqq;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
    private String url = "http://localhost/phpmyadmin/index.php?route=/database/structure&db=checkmate";
    private String db_username = "root";
    private String db_password = "";
    //private  Connection connection = null;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    void connectToDB(String username, String password, String url){

    }
    @FXML
    void login(ActionEvent event) {
        String sql = "SELECT * FROM emplooyees WHERE login = "+this.username;
//        String result = jbdcTemqueryForObject
    }

    @FXML
    void showRegisterStage(MouseEvent event) {

    }

}