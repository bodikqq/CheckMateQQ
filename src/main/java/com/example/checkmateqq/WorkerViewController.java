package com.example.checkmateqq;
import com.example.checkmateqq.triedy.Shift;
import com.example.checkmateqq.triedy.Station;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkerViewController {

    @FXML
    private Label UserNameOnTopBar;

    @FXML
    private Text logoutButton;

    @FXML
    private TextField codeTextField;
    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> stationChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<ShiftForTable> isFirstTable;

    @FXML
    private TableView<?> shiftsTable;

    @FXML
    private ChoiceBox<?> monthsWorkedBox;

    @FXML
    private Label hoursWorkedShow;

    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private List<Station> stations = stationDao.getAll();
    private User user;
    private int selectedStationID = -1;
    private TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private UhsDao uhsDao = DaoFactory.INSTANCE.getUhsDao();
    LocalDate pickedDate;

    @FXML
    void openTestByDate(ActionEvent event) {

    }
    @FXML
    private void initialize() throws EntityNotFoundException{

        datePicker.setDayCellFactory(picker -> new DisabledPastDatesCalendar.DisabledPastDateCell());

        datePicker.setDayCellFactory(picker -> new DisabledPastDatesCalendar.DisabledPastDateCell());

        stationChoiceBox.setValue("Choose the station");
        Map<Integer, String> ourStations = stationsToMap();
        System.out.println("Map Contents: " + ourStations.values());

        stationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Get the corresponding key from the map based on the selected value
                selectedStationID = getKeyByValue(ourStations, newValue);
                //System.out.println("Selected Key: " + selectedKey);
            }
        });


        List<String> qqq = ourStations.values().stream().collect(Collectors.toList());
        stationChoiceBox.getItems().addAll(qqq);
        stationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedStationID = getKeyByValue(ourStations, newValue);
            }
        });
        UserNameOnTopBar.setText(user.getName());

        TableColumn<ShiftForTable, String> column = new TableColumn<ShiftForTable, String> ("Select:");
        column.setCellValueFactory(new PropertyValueFactory<ShiftForTable, String>("isFirst"));
        isFirstTable.getColumns().add(column);

        ShiftForTable isFirst = new ShiftForTable("First shift: 7:00 - 13:00");
        ShiftForTable isSecond = new ShiftForTable("Second shift: 13:00 - 19:00");
        ShiftForTable both = new ShiftForTable("Long shift: 7:00 - 19:00");

        isFirstTable.getItems().add(isFirst);
        isFirstTable.getItems().add(isSecond);
        isFirstTable.getItems().add(both);

        isFirstTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (TableColumn tableColumn: isFirstTable.getColumns()) {
            tableColumn.setReorderable(false);
            tableColumn.setSortable(false);

        }

        isFirstTable.setRowFactory(tv -> {
            TableRow<ShiftForTable> row = new TableRow<>();
            row.minHeightProperty().bind(isFirstTable.minHeightProperty());
            //row.prefHeightProperty().bind(isFirstTable.heightProperty().divide(isFirstTable.getItems().size()));
            return row;
        });
//        TableColumn<ShiftManager,String> stationColumn = new TableColumn<>()



    }

    @FXML
    void onConfirm(ActionEvent event) { //vytvarame user_has_shift
            int tableRowNumber = isFirstTable.getSelectionModel().getSelectedIndex();
            //int stationId = getKeyByValue()
            Date utilDate = java.sql.Date.valueOf(pickedDate);
            Shift shift;
            if(tableRowNumber == 0){
                shiftDao.createShiftIfItDoesentExist(4,utilDate,true);
                shift = shiftDao.getShiftByDateAndIsFirst(utilDate, true);
                uhsDao.createUhsIfDoesntExist(user.getId(),shift.getId());
            }else if(tableRowNumber == 1){
                shiftDao.createShiftIfItDoesentExist(4,utilDate,false);
                shift = shiftDao.getShiftByDateAndIsFirst(utilDate, false);
                uhsDao.createUhsIfDoesntExist(user.getId(),shift.getId());
            }else{

                shiftDao.createShiftIfItDoesentExist(4,utilDate, true);
                shiftDao.createShiftIfItDoesentExist(4,utilDate, false);
                shift = shiftDao.getShiftByDateAndIsFirst(utilDate, true);
                uhsDao.createUhsIfDoesntExist(user.getId(),shift.getId());
                shift = shiftDao.getShiftByDateAndIsFirst(utilDate,false );
                uhsDao.createUhsIfDoesntExist(user.getId(),shift.getId());
        }
    }

    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        goToLogin(stage, loginController);
    }

    @FXML
    void onDatePicked(ActionEvent event) {
        pickedDate = datePicker.getValue();
    }
    private Map<Integer,String> stationsToMap(){
        Map<Integer,String> stationsToMap = new HashMap<Integer,String>();
        for(Station station: stations){
            String town = station.getTown();
            String address = station.getAddress();
            stationsToMap.put(station.getId(),town + ", " + address);
        }
        return stationsToMap;
    }
    private int getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1; // Return a default value or handle the case when the value is not found
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

    public void setUserId(User user) {
        this.user = user;
    }
}

