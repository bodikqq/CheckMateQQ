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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private UhsDao uhsDao = DaoFactory.INSTANCE.getUhsDao();
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private User user;

    public void initialize() throws EntityNotFoundException {
        shiftsToConfirmTableColumns();
        fillShiftsToConfirmTable();
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
            ShiftsForAdmin shiftsForAdmin = new ShiftsForAdmin(stationString,date,time,employee);
            shiftsToConfirmTable.getItems().add(shiftsForAdmin);

        }
        shiftsToConfirmTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

    }
}

