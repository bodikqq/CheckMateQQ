package com.example.checkmateqq;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DisabledPastDatesCalendar extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(picker -> new DisabledPastDateCell());

        StackPane root = new StackPane();
        root.getChildren().add(datePicker);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Disabled Past Dates");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static class DisabledPastDateCell extends DateCell {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (item.isBefore(LocalDate.now().plusDays(1))) {
                setDisable(true);
                setStyle("-fx-background-color: #eeeeee;"); // Change the background color for disabled dates
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
