package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;
import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.Test;
import com.example.checkmateqq.triedy.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class KlientViewController {
    @FXML
    private Text NAATdate;

    @FXML
    private Pane NAATpane;

    @FXML
    private Text NAATqq;

    @FXML
    private Text NAATstation;

    @FXML
    private Text NAATtime;

    @FXML
    private Text PCRdate;

    @FXML
    private Pane PCRpane;

    @FXML
    private Text PCRqq;

    @FXML
    private Text PCRstation;

    @FXML
    private Text PCRtime;

    @FXML
    private Label UserNameOnTopBar;
    @FXML
    private TableView<HourMinutes> timeTable;
    @FXML
    private DatePicker date;
    @FXML
    private Button selectTerm;
    @FXML
    private ChoiceBox<String> station;
    @FXML
    private TableView<ForTestTable> testTable;
    @FXML
    private ChoiceBox<String> typeOfTestChoiceBox;

    private User user;
    private int selectedStationID = -1;
    private TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    //static private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private List<Station> stations = stationDao.getAll();
    String cellData;
    LocalDate pickedDate = null;

    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) selectTerm.getScene().getWindow();
        goToLogin(stage, loginController);
    }

    @FXML
    void pickDate(ActionEvent event) {
        pickedDate = date.getValue();
        if (selectedStationID == -1) return;
        for (TableColumn column : timeTable.getColumns()) {
            columnCellFactory(column);
        }
        unselectCell();
    }

    public void setUserId(User user) {
        this.user = user;
    }

    private int getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1; // Return a default value or handle the case when the value is not found
    }

    public void initialize() throws EntityNotFoundException {
        setPCRtest();
        setNAATtest();
        date.setDayCellFactory(picker -> new DisabledPastDatesCalendar.DisabledPastDateCell());
        station.setValue("Choose the station");
        Map<Integer, String> ourStations = stationsToMap();
        System.out.println("Map Contents: " + ourStations.values());

        List<String> qqq = ourStations.values().stream().collect(Collectors.toList());
        station.getItems().addAll(qqq);
        station.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Get the corresponding key from the map based on the selected value
                selectedStationID = getKeyByValue(ourStations, newValue);
                unselectCell();
                if(pickedDate!= null){
                    for (TableColumn column : timeTable.getColumns()) {
                        columnCellFactory(column);
                    }
                }
            }
        });


        UserNameOnTopBar.setText(user.getName());

        typeOfTestChoiceBox.setValue("Test type");
        typeOfTestChoiceBox.getItems().setAll("PCR", "NAATs");

        changeColorBack(station);
        changeColorBack(typeOfTestChoiceBox);
        dateColorBack(date);

        timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        testTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn<ForTestTable, Date> testDateColumn = new TableColumn<ForTestTable, Date>("Date");
        testDateColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, Date>("date"));

        TableColumn<ForTestTable, String> testResultColumn = new TableColumn<ForTestTable, String>("Result");
        testResultColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, String>("result"));

        TableColumn<ForTestTable, String> testTypeColumn = new TableColumn<ForTestTable, String>("Test type");
        testTypeColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, String>("test_type"));

        testTable.getColumns().add(testDateColumn);
        testTable.getColumns().add(testTypeColumn);
        testTable.getColumns().add(testResultColumn);

        for (TableColumn tableColumn : testTable.getColumns()) {
            tableColumn.setReorderable(false);
            tableColumn.setSortable(false);
//            tableColumn.setResizable(false);
        }
        List<Test> tests = testDao.getTestsOlderThanTenMinutes(user.getId());
        Test testForSortHaha = new Test();
        tests = testForSortHaha.sortByTime(tests);
        for (Test test : tests
        ) {
            String result = "Not ready yet";
            if (test.getResult() == 1) result = "positive";
            else if (test.getResult() == 2) result = "negative";
            String type = "PCR";

            int test_type = test.getTest_type();
            if (test_type == 1) type = "NAATs";
            ForTestTable qq = new ForTestTable(test.getDate(), result, type);
            //System.out.println(qq.getDate());
            testTable.getItems().add(qq);
        }
        System.out.println("woohoo");

        TableColumn<HourMinutes, String> time0 = new TableColumn<HourMinutes, String>();
        time0.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time0"));

        TableColumn<HourMinutes, String> time1 = new TableColumn<HourMinutes, String>();
        time1.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time1"));

        TableColumn<HourMinutes, String> time2 = new TableColumn<HourMinutes, String>();
        time2.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time2"));

        TableColumn<HourMinutes, String> time3 = new TableColumn<HourMinutes, String>();
        time3.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time3"));

        TableColumn<HourMinutes, String> time4 = new TableColumn<HourMinutes, String>();
        time4.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time4"));

        TableColumn<HourMinutes, String> time5 = new TableColumn<HourMinutes, String>();
        time5.setCellValueFactory(new PropertyValueFactory<HourMinutes, String>("time5"));

        timeTable.getColumns().addAll(time0, time1, time2, time3, time4, time5);
        for (TableColumn tableColumn : timeTable.getColumns()) {
            tableColumn.setReorderable(false);
            tableColumn.setSortable(false);
            //  tableColumn.setResizable(false);
        }

        for (int i = 7; i < 19; i++) {
            String HourMinuteString = "0" + i + ":" + 0 + "0";
            String HourMinuteString1 = "0" + i + ":" + 1 + "0";
            String HourMinuteString2 = "0" + i + ":" + 2 + "0";
            String HourMinuteString3 = "0" + i + ":" + 3 + "0";
            String HourMinuteString4 = "0" + i + ":" + 4 + "0";
            String HourMinuteString5 = "0" + i + ":" + 5 + "0";
            if (i >= 10) {
                HourMinuteString = i + ":" + 0 + "0";
                HourMinuteString1 = i + ":" + 1 + "0";
                HourMinuteString2 = i + ":" + 2 + "0";
                HourMinuteString3 = i + ":" + 3 + "0";
                HourMinuteString4 = i + ":" + 4 + "0";
                HourMinuteString5 = i + ":" + 5 + "0";
            }
            HourMinutes hourminutes = new HourMinutes(HourMinuteString, HourMinuteString1, HourMinuteString2, HourMinuteString3, HourMinuteString4, HourMinuteString5);
            //System.out.println(hourminutes.toString());
            timeTable.getItems().add(hourminutes);
        }
//        timeTable.lookup(".column-header-background").setVisible(false);
//        timeTable.lookup(".show-hide-columns-button").setVisible(false);
        timeTable.getSelectionModel().setCellSelectionEnabled(true);

        ObservableList<TablePosition> selectedCells = timeTable.getSelectionModel().getSelectedCells();
        timeTable.getStyleClass().add("noheader");
        selectedCells.addListener((ListChangeListener.Change<? extends TablePosition> change) -> {
            if (selectedCells.size() > 0) {
                TablePosition selectedCell = selectedCells.get(0);
                TableColumn<HourMinutes, String> column = (TableColumn<HourMinutes, String>) selectedCell.getTableColumn();
                int rowIndex = selectedCell.getRow();
                cellData = column.getCellObservableValue(rowIndex).getValue();
            }
        });


    }

    private List<Test> getTestData(int user_id) throws EntityNotFoundException {
        List<Test> result = testDao.getAllUserTests(user_id);
        return result;
    }

    @FXML
    void saveTestTerm(ActionEvent event) throws EntityNotFoundException {
        String testType = typeOfTestChoiceBox.getValue();
        Integer chosenTestType = null;
        if (testType.equals("PCR")) {
            chosenTestType = 0;
        } else if (testType.equals("NAATs")) {
            chosenTestType = 1;
        }
        System.out.println(cellData + pickedDate + chosenTestType + station.getValue());
        if (cellData == null || pickedDate == null || selectedStationID == -1 || chosenTestType == null) {
            // Set error styles for controls that are not selected
            if (cellData == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select time of the test");
                alert.show();
            }

            if (pickedDate == null) {
                date.getStyleClass().add("error-date-picker");
            }

            if (selectedStationID == -1) {
                station.getStyleClass().add("error-choice-box");
            }

            if (chosenTestType == null) {
                typeOfTestChoiceBox.getStyleClass().add("error-choice-box");
            }

        } else {
            Test NAATtest = testDao.getUsersNAATsTest(user.getId());
            Test PCRtest = testDao.getUsersPCRTest(user.getId());
            if (NAATtest == null) {
                saveTest();
                unselectCell();
                setNAATtest();
                timeTable.getSelectionModel().clearSelection();
                for (TableColumn column : timeTable.getColumns()) {
                    columnCellFactory(column);
                }
            } else if(PCRtest == null){
                saveTest();
                unselectCell();
                setPCRtest();
                timeTable.getSelectionModel().clearSelection();
                for (TableColumn column : timeTable.getColumns()) {
                    columnCellFactory(column);
                }
            }else {
                if(NAATtest != null && chosenTestType == 1){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null); // No header text
                    alert.setContentText("NAAT test already scheduled");
                    alert.showAndWait();
                }
                System.out.println(PCRtest);
                if(PCRtest != null && chosenTestType == 0){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null); // No header text
                    alert.setContentText("PCR test already scheduled");
                    alert.showAndWait();
                }
            }

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

    private void goToBalance(Stage currentStage, CardViewController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("CardView.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
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
    private Map<Integer, String> stationsToMap() {
        Map<Integer, String> stationsToMap = new HashMap<Integer, String>();
        for (Station station : stations) {
            String town = station.getTown();
            String address = station.getAddress();
            stationsToMap.put(station.getId(), town + ", " + address);
        }
        return stationsToMap;
    }

    private void saveTest() throws EntityNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(cellData, formatter);
        Date utilDate = java.sql.Date.valueOf(pickedDate);
        Time utilTime = java.sql.Time.valueOf(localTime);
        String testType = typeOfTestChoiceBox.getValue();
        Integer chosenTestType = null;
        if (testType.equals("PCR")) {
            chosenTestType = 0;
        } else if (testType.equals("NAATs")) {
            chosenTestType = 1;
        }
        Test test = new Test(utilDate, user.getId(), utilTime, chosenTestType);
//        shiftDao.createShiftIfItDoesentExist(selectedStationID,utilDate,0);
//        shiftDao.createShiftIfItDoesentExist(selectedStationID,utilDate,1);
        Shift shift;
        Boolean isFirst = true;
        LocalTime mmm = LocalTime.parse("12:55", formatter);
        if (localTime.isAfter(mmm)) isFirst = false;
        shift = shiftDao.getShiftByDateAndIsFirst(utilDate, isFirst);
        test.setShift_id(shift.getId());
        testDao.save(test);
    }

    private void changeColorBack(ChoiceBox choiceBox) {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!"Choose the station".equals(newValue)) {
                choiceBox.getStyleClass().remove("error-choice-box");
                choiceBox.getStyleClass().add("normal-choice-box");
            }
        });
    }

    private void dateColorBack(DatePicker datePicker) {
//        datePicker.setPromptText("Choose the date");

        // Add a listener to the DatePicker to detect changes in the selected date
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                datePicker.getStyleClass().remove("error-date-picker");
                datePicker.getStyleClass().add("normal-date-picker");
            }
        });
    }

    private void cellIteration() {
        for (int rowIndex = 0; rowIndex < timeTable.getItems().size(); rowIndex++) {
            // Iterate through columns
            for (int colIndex = 0; colIndex < timeTable.getColumns().size(); colIndex++) {
                TableColumn<HourMinutes, String> column = (TableColumn<HourMinutes, String>) timeTable.getColumns().get(colIndex);
                ObservableValue<String> cellm = column.getCellObservableValue(rowIndex);
                String cell = cellm.getValue();
                // Now you have access to the cell, and you can perform any actions you need
                // For example, you can check the data in the cell
                Time time = convertStringToTime(cell);
                LocalTime localTime = time.toLocalTime();
                Date utilDate = java.sql.Date.valueOf(pickedDate);
                boolean is_first = false;
                if (localTime.isBefore(LocalTime.of(13, 0))) {
                    is_first = true;
                }
                if (testDao.testsOnTime(time, utilDate) == userDao.workersOnTime(utilDate, selectedStationID, is_first)) {
                    /////
                }

                System.out.println(time);
            }
        }
    }

    private Time convertStringToTime(String timeString) {
        // Parse the string representation of time to LocalTime
        LocalTime localTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        // Convert LocalTime to Time
        return Time.valueOf(localTime);
    }

    private static class DisabledPastDateCell extends DateCell {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (item.isBefore(LocalDate.now())) {
                setDisable(true);
                setStyle("-fx-background-color: #eeeeee;"); // Change the background color for disabled dates
            }
        }
    }

    @FXML
    void addBalance(MouseEvent event) {
        CardViewController controller = new CardViewController();
        Stage stage = (Stage) selectTerm.getScene().getWindow();
        goToBalance(stage,controller);
    }
    private void columnCellFactory(TableColumn column) {
        Date utilDate = java.sql.Date.valueOf(pickedDate);
        final int workers_now = userDao.workersOnTime(utilDate, selectedStationID, true);

        column.setCellFactory(tc -> new TableCell<HourMinutes, String>() {
            private final ExecutorService executor = Executors.newFixedThreadPool(5); // Adjust the thread pool size as needed

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setDisable(false);
                    setText("");
                } else {
                    // Perform your condition check here
                    Time time = convertStringToTime(item);
                    LocalTime localTime = time.toLocalTime();

                    // Check if the time in the cell is before the current time
                    if (localTime.isBefore(LocalTime.now())) {
                        setDisable(true);
                        setText(item);
                        setTextFill(Color.web("#b9b9b9"));
                        setStyle("-fx-background-color: #eeeeee;");
                    } else {
                        int workersCount = workers_now;

                        if (localTime.equals(LocalTime.of(13, 0))) {
                            Future<Integer> future = executor.submit(() ->
                                    userDao.workersOnTime(utilDate, selectedStationID, false));

                            try {
                                workersCount = future.get();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Future<Integer> testsFuture = executor.submit(() ->
                                testDao.testsOnTime(time, utilDate));

                        try {
                            int testsCount = testsFuture.get();

                            if (testsCount >= workersCount) {
                                setDisable(true);
                                setTextFill(Color.web("#b9b9b9"));
                                setStyle("-fx-background-color: #eeeeee;");
                            } else {
                                setDisable(false);
                            }

                            setText(item);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private void setPCRtest() {
        Test PCRtest = testDao.getUsersPCRTest(user.getId());
        if (PCRtest == null) return;
        int shift_id = PCRtest.getShift_id();
        Shift shift = shiftDao.getShiftByShiftID(shift_id);
        int station_id = shift.getStation_id();
        Station stationQQ = stationDao.getStationById(station_id);
        String stationName = stationQQ.toString();
        PCRdate.setText(PCRtest.getDate().toString());
        PCRtime.setText(PCRtest.getTime().toString());
        PCRstation.setText(stationName);
        PCRpane.setVisible(true);
        PCRqq.setVisible(true);
    }
    private void setNAATtest() {
        Test NAATtest = testDao.getUsersNAATsTest(user.getId());
        if (NAATtest == null) return;
        int shift_id = NAATtest.getShift_id();
        Shift shift = shiftDao.getShiftByShiftID(shift_id);
        int station_id = shift.getStation_id();
        Station stationQQ = stationDao.getStationById(station_id);
        String stationName = stationQQ.toString();
        NAATdate.setText(NAATtest.getDate().toString());
        NAATtime.setText(NAATtest.getTime().toString());
        NAATstation.setText(stationName);
        NAATpane.setVisible(true);
        NAATqq.setVisible(true);
    }

    private void unselectCell() {
        timeTable.getSelectionModel().clearSelection();
    }
}
