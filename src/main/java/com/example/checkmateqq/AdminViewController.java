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

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @FXML
    private Text worker_code;
    private UhsDao uhsDao = DaoFactory.INSTANCE.getUhsDao();
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private Map<Integer, String> stationMap = new HashMap<>();
    private User user;

    private CodeDAO codeDAO = DaoFactory.INSTANCE.getCodeDao();

    public void initialize() throws EntityNotFoundException {
        System.out.println("Initializing AdminViewController");
        shiftsToConfirmTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        shiftsToConfirmTableColumns();
        fillShiftsToConfirmTable();
        fillStationList();

    }
    @FXML
    void copyClipboard(MouseEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(worker_code.getText().toString());
        clipboard.setContent(content);
    }

    @FXML
    void generateEmployeeCode(ActionEvent event) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        worker_code.setText(code.toString());
        worker_code.setVisible(true);
        codeDAO.addNewWorkerCode(code.toString());
    }

    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        goToLogin(stage, loginController);
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
            searchStage.setTitle("Employee manager");
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

    @FXML
    void onShiftConfirm(ActionEvent event) {
        List<ShiftsForAdmin> shifts = (List<ShiftsForAdmin>) shiftsToConfirmTable.getSelectionModel().getSelectedItems();
        for (ShiftsForAdmin shiftsForAdmin :shifts) {
            System.out.println(shiftsForAdmin.getId());
            uhsDao.updateIsConfirmed(shiftsForAdmin.getEmployeeId(), shiftsForAdmin.getId(), true);
            System.out.println(user.getId());

        }
        shiftsToConfirmTable.getItems().removeAll(shifts);
        //uhsDao.updateIsConfirmed(user.getId(), shift.getId(), true);
    }

    private void shiftsToConfirmTableColumns() {
        TableColumn<ShiftsForAdmin, String> stationColumn = new TableColumn<ShiftsForAdmin, String>("place");
        stationColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("station"));
        shiftsToConfirmTable.getColumns().add(stationColumn);
        stationColumn.setPrefWidth(115);

        TableColumn<ShiftsForAdmin, Date> dateColumn = new TableColumn<ShiftsForAdmin, Date>("date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, Date>("date"));
        shiftsToConfirmTable.getColumns().add(dateColumn);
        dateColumn.setPrefWidth(115);

        TableColumn<ShiftsForAdmin, String> timeColumn = new TableColumn<ShiftsForAdmin, String>("time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("time"));
        shiftsToConfirmTable.getColumns().add(timeColumn);
        timeColumn.setPrefWidth(115);

        TableColumn<ShiftsForAdmin, String> employeeColumn = new TableColumn<ShiftsForAdmin, String>("employee");
        employeeColumn.setCellValueFactory(new PropertyValueFactory<ShiftsForAdmin, String>("employee"));
        shiftsToConfirmTable.getColumns().add(employeeColumn);
        employeeColumn.setPrefWidth(115);
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
            int employeeId = uhs.getUser_id();
            String employee = user.getName() + " " + user.getSurname();
            ShiftsForAdmin shiftsForAdmin = new ShiftsForAdmin(shiftId,stationString,date,time,employee,employeeId);
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
                if (value.equals(selectedStationString)) {
                    stationId = key;
                }
            }
            if (!stationDao.hasShifts(stationId)) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation Dialog");
                confirmationAlert.setHeaderText("Are you sure you want to proceed?");

                // Add OK and Cancel buttons
                confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

                // Show the confirmation alert and wait for the user's response
                confirmationAlert.showAndWait();

                // Check the user's response
                if (confirmationAlert.getResult() == ButtonType.OK) {
                    System.out.println("User clicked OK" + stationId);

                    stationDao.deleteStation(stationId);
                    stationListVIew.getItems().remove(selectedStationString);
                } else {
                    System.out.println("User clicked Cancel");
                    confirmationAlert.close();
                }

            } else {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("");
                confirmationAlert.setHeaderText("The Station is still active.");
                confirmationAlert.show();
            }
        }else{
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

