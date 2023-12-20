package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;
import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.Uhs;
import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class AdminViewController {

    @FXML
    private Label UserNameOnTopBar;
    @FXML
    private TableView<ShiftsForAdmin> shiftsToConfirmTable;
    @FXML
    private Text logOutButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button openEmpManagerButton;
    @FXML
    private ListView<String> stationListVIew;
    @FXML
    private TextField addStationTextField;
    @FXML
    private Button addStationButton;
    @FXML
    private Button deleteStationButton;
    private UhsDao uhsDao = DaoFactory.INSTANCE.getUhsDao();
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private Map<Integer, String> stationMap = new HashMap<>();
    private User user;

    public void initialize() throws EntityNotFoundException {
        shiftsToConfirmTableColumns();
        fillShiftsToConfirmTable();
        fillStationList();
    }

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

    @FXML
    void onShiftConfirm(ActionEvent event) {
        List<ShiftsForAdmin> shifts = (List<ShiftsForAdmin>) shiftsToConfirmTable.getSelectionModel().getSelectedItems();
        for (ShiftsForAdmin shiftsForAdmin :shifts) {
            System.out.println(shiftsForAdmin.getId());
            uhsDao.updateIsConfirmed(user.getId(), shiftsForAdmin.getId(), true);

        }
        shiftsToConfirmTable.getItems().removeAll(shifts);
        //uhsDao.updateIsConfirmed(user.getId(), shift.getId(), true);
    }

    private void shiftsToConfirmTableColumns() {
        TableColumn<ShiftsForAdmin, String> stationColumn = new TableColumn<ShiftsForAdmin, String>("place");
        stationColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("station"));
        shiftsToConfirmTable.getColumns().add(stationColumn);
        stationColumn.setPrefWidth(106);

        TableColumn<ShiftsForAdmin, Date> dateColumn = new TableColumn<ShiftsForAdmin, Date>("date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, Date>("date"));
        shiftsToConfirmTable.getColumns().add(dateColumn);
        dateColumn.setPrefWidth(106);

        TableColumn<ShiftsForAdmin, String> timeColumn = new TableColumn<ShiftsForAdmin, String>("time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("time"));
        shiftsToConfirmTable.getColumns().add(timeColumn);
        timeColumn.setPrefWidth(106);

        TableColumn<ShiftsForAdmin, String> employeeColumn = new TableColumn<ShiftsForAdmin, String>("employee");
        employeeColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("employee"));
        shiftsToConfirmTable.getColumns().add(employeeColumn);
        employeeColumn.setPrefWidth(106);
        //426
    }
    private void fillShiftsToConfirmTable() throws EntityNotFoundException {
        List<Uhs> uhsList = uhsDao.getAllUhs();
        for (Uhs uhs:uhsList) {
            int shiftId = uhs.getShift_id();
            Shift shift = shiftDao.getShiftByShiftID(uhs.getShift_id());
            Station station = stationDao.getStationById(shift.getStation_id());
            String stationString = station.toString2();
            Date date = shift.getDate();
            String time = "";
            if(shift.isFirst()){
                time = "7:00 - 13:00";
            }else if(!shift.isFirst()) {
                time = "13:00 - 19:00";
            }
            User user = userDao.getById(uhs.getUser_id());
            String employee = user.getName() + " " + user.getSurname();
            ShiftsForAdmin shiftsForAdmin = new ShiftsForAdmin(shiftId, stationString,date,time,employee);
            shiftsToConfirmTable.getItems().add(shiftsForAdmin);

        }
        shiftsToConfirmTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

    }
    @FXML
    void onAddStation(ActionEvent event) {
        String stationToAdd = addStationTextField.getText();

        if (isValidStationFormat(stationToAdd)) {
            // Split the station string into town, street, and street number
            String[] stationParts = stationToAdd.split(",\\s");

            if (stationParts.length == 3) {
                String town = stationParts[0];
                String street = stationParts[1];
                String streetNumber = stationParts[2];

                // Create a new Station object
                Station newStation = new Station(0, town, street + " " + streetNumber);

                // Add the new station to the database
                stationDao.createStation(newStation);

                // Add the new station to the ListView
                stationListVIew.getItems().add(newStation.toString());
            } else {
                showAlert("Invalid Format", "Please enter the station in the format: 'Town, Street, Street Number'");
            }
        } else {
            showAlert("Invalid Format", "Please enter the station in the format: 'Town, Street, Street Number'");
        }
    }
    private boolean isValidStationFormat(String stationString) {
        return stationString.matches("^\\w+,\\s\\w+,\\s\\w+$");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void onDeleteStation(ActionEvent event) {
        String selectedStationString = stationListVIew.getSelectionModel().getSelectedItem();

        if (selectedStationString != null) {
            int stationId = 0;
            for (Map.Entry<Integer, String> entry : stationMap.entrySet()) {
                int key = entry.getKey();
                String value = entry.getValue();
                if(value.equals(selectedStationString)){
                    stationId = key;
                }
            }
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation Dialog");
            confirmationAlert.setHeaderText("Are you sure you want to proceed?");

            // Add OK and Cancel buttons
            confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Show the confirmation alert and wait for the user's response
            confirmationAlert.showAndWait();

            // Check the user's response
            if (confirmationAlert.getResult() == ButtonType.OK) {
                System.out.println("User clicked OK");
                stationDao.deleteStation(stationId);
                stationListVIew.getItems().remove(selectedStationString);
            } else {
                System.out.println("User clicked Cancel");
                confirmationAlert.close();
            }

        } else {
            showAlert("No Station Selected", "Please select a station to delete.");
        }
    }

    private void fillStationList() {
        //if (stationListVIew != null) {  // Check if stationListVIew is not null
            stationListVIew.getItems().clear();  // Clear existing items before adding new ones

            for (Station station : stationDao.getAll()) {
                String stationString = station.toString();
                stationMap.put(station.getId(),stationString);
                stationListVIew.getItems().add(stationString);
            }

    }
    private void showConfirmationAlert() {
        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Are you sure you want to proceed?");

        // Add OK and Cancel buttons
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Show the confirmation alert and wait for the user's response
        confirmationAlert.showAndWait();

        // Check the user's response
        if (confirmationAlert.getResult() == ButtonType.OK) {
            System.out.println("User clicked OK");
            // Perform the action you want to take when the user clicks OK
        } else {
            System.out.println("User clicked Cancel");
            // Perform the action you want to take when the user clicks Cancel
        }
    }
}

