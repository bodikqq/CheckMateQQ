package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Test;
import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SearchForTestViewController {
    TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    User user = null;
    Test currentTest;
    @FXML
    private ImageView IconSearch;

    @FXML
    private TextField TestIdField;

    @FXML
    private Text ThereIsNoTestText;

    @FXML
    private Text id;

    @FXML
    private Text name;

    @FXML
    private Button submitButton;

    @FXML
    private Text surname;
    @FXML
    private AnchorPane texts;

    @FXML
    private ChoiceBox<String> testResult;

    @FXML
    private Text type;
    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;


    @FXML
    void change(InputMethodEvent event) {

    }

    @FXML
    void searchForTestByID(MouseEvent event) {
        searchForTest();
    }
    private void searchForTest(){
        String test_id = TestIdField.getText();
        long id;
        try {
            ThereIsNoTestText.setVisible(false);
            id = Long.parseLong(test_id);
            Test test = testDao.getTestById(id);
            if(test == null){
                hideAll();
                ThereIsNoTestText.setVisible(true);
                return;
            }
            currentTest = test;
            this.id.setText(test_id);
            User patient = userDao.getById(currentTest.getPatient_id());
            name.setText(patient.getName());
            surname.setText(patient.getSurname());
            type.setText("PCR");
            if(test.getTest_type() == 1)type.setText("NAATs");
            showAll();
            if(test.getResult() == 1)testResult.setValue("Positive");
            if(test.getResult() == 2)testResult.setValue("Negative");


        } catch (NumberFormatException e) {
            hideAll();
            ThereIsNoTestText.setVisible(true);
            return;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void submit(ActionEvent event) throws EntityNotFoundException {
        if(testResult.getValue() == "Choose result") {
            ///////
            return;
        }
        int testResultq = 2;
        if(testResult.getValue() == "Positive")testResultq = 1;
        currentTest.setResult(testResultq);
        testDao.updateTestResultById(currentTest.getId(),currentTest.getResult());


        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
    public void hideAll(){
        text1.setVisible(false);
        text2.setVisible(false);
        text3.setVisible(false);
        text4.setVisible(false);
        name.setVisible(false);
        surname.setVisible(false);
        id.setVisible(false);
        type.setVisible(false);
        testResult.setVisible(false);
        submitButton.setVisible(false);
        texts.setVisible(false);
    }
    public void showAll(){
        text1.setVisible(true);
        text2.setVisible(true);
        text3.setVisible(true);
        text4.setVisible(true);
        name.setVisible(true);
        surname.setVisible(true);
        id.setVisible(true);
        type.setVisible(true);
        testResult.setVisible(true);
        submitButton.setVisible(true);
        texts.setVisible(true);
    }
    private void initializeTestResultChoiceBox() {
        testResult.getItems().addAll( "Positive", "Negative");
        testResult.setValue("Choose result");
    }
    private void enterPressed(){
        TestIdField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("qqqq");
                    searchForTest();
                }
            }
        });
    }
    public void initialize(){
        enterPressed();
        hideAll();
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TestIdField.setTextFormatter(textFormatter);
        initializeTestResultChoiceBox();
    }
    public void setUser(User user) {
        this.user = user;
    }
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (Pattern.matches("[0-9]*", newText)) {
            return change;
        } else {
            return null;
        }
    };

}
