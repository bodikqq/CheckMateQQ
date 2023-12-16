package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class EmpManagerViewController {

    @FXML
    private TableView<EmployeeForTable> employeeTable;

    @FXML
    private ImageView IconSearch;

    @FXML
    private ListView<?> pastShiftsList;

    @FXML
    private ListView<?> upcomingShiftsList;
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private int selectedId;
    @FXML
    private void initialize() throws EntityNotFoundException {
        employeeTableColumns();
        fillEmployeeTable();

        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Handle the selection change
                        selectedId = newValue.getId();
                        System.out.println("Selected ID: " + selectedId);
                    }
                });
    }
    @FXML
    void change(InputMethodEvent event) {

    }

    @FXML
    void onCancelShift(ActionEvent event) {

    }

    @FXML
    void onDeleteAccount(ActionEvent event) throws EntityNotFoundException {
        userDao.deleteUserById(selectedId);
        employeeTable.getItems().clear();
        fillEmployeeTable();
    }

    @FXML
    void onEmployeeLookUp(ActionEvent event) {

    }

    @FXML
    void searchForTestByID(MouseEvent event) {

    }

    public void setUserId(User user) {
    }
    private void fillEmployeeTable(){
        for(User user: userDao.returnEmployees()){
            Integer id = user.getId();
            String fullName = user.getName() + " " + user.getSurname();
            EmployeeForTable employee = new EmployeeForTable(id,fullName);
            employeeTable.getItems().add(employee);
        }

    }
    private void employeeTableColumns(){
        employeeTable.setPrefWidth(190);
        TableColumn<EmployeeForTable, Integer> idColumn = new TableColumn<EmployeeForTable, Integer>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, Integer>("id"));
        employeeTable.getColumns().add(idColumn);
        idColumn.setPrefWidth(80);

        TableColumn<EmployeeForTable, String> nameColumn = new TableColumn<EmployeeForTable, String>("id");
        nameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("fullName"));
        employeeTable.getColumns().add(nameColumn);
        nameColumn.setPrefWidth(157);
    }
}

