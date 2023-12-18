package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;
import com.example.checkmateqq.triedy.Uhs;
import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpManagerViewController {

    @FXML
    private TableView<EmployeeForTable> employeeTable;

    @FXML
    private ImageView IconSearch;

    @FXML
    private ListView<String> pastShiftsList;

    @FXML
    private TextField searchText;
    @FXML
    private ListView<String> upcomingShiftsList;
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private UhsDao uhsDao = DaoFactory.INSTANCE.getUhsDao();
    private Map<Integer, String> upcomingShiftsMap = new HashMap<>();
    private int selectedId;

    @FXML
    private void initialize() throws EntityNotFoundException {
        employeeTableColumns();
        fillEmployeeTable();
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            fillSearchedEmployeeTable(newValue);
        });
        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Handle the selection change
                        selectedId = newValue.getId();
                        System.out.println("Selected ID: " + selectedId);
                        fillPastShiftsList();
                        fillUpcomingShiftsList();
                    }
                });

    }

    @FXML
    void change(InputMethodEvent event) {

    }

    @FXML
    void onCancelShift(ActionEvent event) {
        String selectedString = upcomingShiftsList.getSelectionModel().getSelectedItem();
        int selectedShiftId = 0;
        for (Map.Entry<Integer, String> entry : upcomingShiftsMap.entrySet()) {
            Integer shiftId = entry.getKey();
            String shiftString = entry.getValue();
            if (selectedString.equals(shiftString)) {
                selectedShiftId = shiftId;
            }
        }
        uhsDao.deleteUhsByShiftIdAndUserId(selectedShiftId,selectedId);
        upcomingShiftsList.getItems().clear();
        fillUpcomingShiftsList();

    }

    @FXML
    void onDeleteAccount(ActionEvent event) throws EntityNotFoundException {
        if (userDao.hasUpcomingShifts(selectedId)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Employee still has upcoming shifts.");
            alert.show();
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setHeaderText("Are you sure you want to delete the user?");
            confirmationAlert.setContentText("Click OK to proceed, or Cancel to abort.");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("User clicked OK. Proceeding...");
                    try {
                        userDao.deleteUserById(selectedId);
                    } catch (EntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (response == ButtonType.CANCEL) {
                    System.out.println("User clicked Cancel. Aborting...");
                    confirmationAlert.close();
                }
            });
        }

        employeeTable.getItems().clear();
        fillEmployeeTable();

    }

    @FXML
    void onEmployeeLookUp(ActionEvent event) {
        fillSearchedEmployeeTable(searchText.getText());

    }

    @FXML
    void searchForTestByID(MouseEvent event) {
        fillSearchedEmployeeTable(searchText.getText());
    }

    public void setUserId(User user) {
    }

    private void fillEmployeeTable() {
        for (User user : userDao.returnEmployees()) {
            Integer id = user.getId();
            String fullName = user.getName() + " " + user.getSurname();
            EmployeeForTable employee = new EmployeeForTable(id, fullName);
            employeeTable.getItems().add(employee);
        }

    }
    private void fillSearchedEmployeeTable(String searchString){
        employeeTable.getItems().clear();
        List<User> workers = userDao.searchUserByNameSurnameOrId(searchString);
        if(workers == null)return;
        for(User user: workers){
            Integer id = user.getId();
            String fullName = user.getName() + " " + user.getSurname();
            EmployeeForTable employee = new EmployeeForTable(id,fullName);
            employeeTable.getItems().add(employee);
        }
    }

    private void employeeTableColumns() {
        employeeTable.setPrefWidth(190);
        TableColumn<EmployeeForTable, Integer> idColumn = new TableColumn<EmployeeForTable, Integer>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, Integer>("id"));
        employeeTable.getColumns().add(idColumn);
        idColumn.setPrefWidth(80);

        TableColumn<EmployeeForTable, String> nameColumn = new TableColumn<EmployeeForTable, String>("Employee");
        nameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("fullName"));
        employeeTable.getColumns().add(nameColumn);
        nameColumn.setPrefWidth(157);
    }

    private void fillPastShiftsList() {
        List<String> pastShiftsData = new ArrayList<>();
        List<Shift> shifts = shiftDao.getPastShiftsForUser(selectedId);
        if(shifts == null)return;
        for (Shift shift : shifts) {
            String cas = "";
            if (shift.isFirst()) {
                cas = "first";
            } else if (!shift.isFirst()) {
                cas = "second";
            }
            String shiftString = shift.getDate() + ", " + cas + ", " + (stationDao.getStationById(shift.getStation_id()).toString2());
            pastShiftsData.add(shiftString);
        }
        pastShiftsList.getItems().setAll(pastShiftsData);
    }


    private void fillUpcomingShiftsList() {

        upcomingShiftsMap.clear();
        List<String> upcomingShiftsData = new ArrayList<>();
        List<Shift> upcShifts = shiftDao.getFutureShiftsForUser(selectedId);
        if(upcShifts == null)return;
        for (Shift shift : upcShifts) {
            String cas = "";
            if (shift.isFirst()) {
                cas = "7:00  - 13:00";
            } else if (!shift.isFirst()) {
                cas = "13:00 - 19:00";
            }
            String shiftString = shift.getDate() + ", " + cas + ", " + (stationDao.getStationById(shift.getStation_id()).toString());
            upcomingShiftsMap.put(shift.getId(), shiftString);
            upcomingShiftsData.add(shiftString);
        }
        if(upcomingShiftsData.isEmpty() || upcomingShiftsData == null)return;
        upcomingShiftsMap.forEach((key, value) -> System.out.println(key + ":" + value));
        upcomingShiftsList.getItems().setAll(upcomingShiftsData);
    }
}

