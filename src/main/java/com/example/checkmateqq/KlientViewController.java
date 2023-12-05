package com.example.checkmateqq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

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
    void pickDate(ActionEvent event) {

    }

    @FXML
    void saveTestTerm(ActionEvent event) {

    }
}
