package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.Test;
import com.example.checkmateqq.triedy.User;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// class ForTestTable{
//     public Date getDate() {
//         return date;
//     }
//
//     // Setter for 'date'
//     public void setDate(Date date) {
//         this.date = date;
//     }
//
//     public String getTestResult() {
//         return testResult;
//     }
//
//     public void setTestResult(String testResult) {
//         this.testResult = testResult;
//     }
//
//     ForTestTable(Date date, String testResult){
//         this.date = date;
//         this.testResult = testResult;
//     }
//    private Date date;
//    private String testResult;
//
//}


public class KlientViewController {
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
    private TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    static private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private List<Station> stations = stationDao.getAll();
    String cellData;
    LocalDate pickedDate;
    String chosenStation;
    int chosenTestType;
    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) selectTerm.getScene().getWindow();
        goToLogin(stage, loginController);
    }
    @FXML
    void pickDate(ActionEvent event) {
        pickedDate = date.getValue();
    }
    public void setUserId(User user) {
        this.user = user;
    }
    public void initialize() throws EntityNotFoundException {
        station.setValue("Choose the station");
        station.getItems().setAll(stationsToString());
       // System.out.println(user.get);
        chosenStation = station.getValue();

        UserNameOnTopBar.setText(user.getName());

//        typeOfTestChoiceBox.setValue("Test type");
//        typeOfTestChoiceBox.getItems().setAll("PCR","NAATs");
//        String testType = typeOfTestChoiceBox.getValue();
//        if(testType.equals("PCR")){chosenTestType = 0;}else{chosenTestType=1;}


        TableColumn<ForTestTable, Date> testDateColumn = new TableColumn<ForTestTable, Date> ("Date");
        testDateColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, Date>("date"));

        TableColumn<ForTestTable, String> testResultColumn = new TableColumn<ForTestTable, String> ("Result");
        testResultColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, String>("result"));

        TableColumn<ForTestTable, String> testTypeColumn = new TableColumn<ForTestTable, String> ("Test type");
        testTypeColumn.setCellValueFactory(new PropertyValueFactory<ForTestTable, String>("test_type"));

        testTable.getColumns().add(testDateColumn);
        testTable.getColumns().add(testTypeColumn);
        testTable.getColumns().add(testResultColumn);
        List<Test> tests = getTestData(user.getId());
        for (Test test:tests
             ) {
            String result = "negative";
            if (test.getResult() == 1) result = "positive";
            else if (test.getResult() == 2) result = "Not ready yet";
            String type = "PCR";

            int test_type = test.getTest_type();
            if(test_type == 1)type = "NAATs";
            ForTestTable qq = new ForTestTable(test.getDate(), result,type);
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

        timeTable.getColumns().addAll(time0,time1,time2,time3,time4,time5);

        for (int i = 7; i < 19; i++) {
            String HourMinuteString = "0" + i + ":" + 0 + "0";
            String HourMinuteString1 = "0" + i + ":" + 1 + "0";
            String HourMinuteString2 = "0" + i + ":" + 2 + "0";
            String HourMinuteString3 = "0" + i + ":" + 3 + "0";
            String HourMinuteString4 = "0" + i + ":" + 4 + "0";
            String HourMinuteString5 = "0" + i + ":" + 5 + "0";
            if(i>=10){
                HourMinuteString = i+":"+0+"0";
                HourMinuteString1 = i+":"+1+"0";
                HourMinuteString2 =i+":"+2+"0";
                HourMinuteString3 = i+":"+3+"0";
                HourMinuteString4 = i+":"+4+"0";
                HourMinuteString5 = i+":"+5+"0";
            }
            HourMinutes hourminutes = new HourMinutes(HourMinuteString,HourMinuteString1,HourMinuteString2,HourMinuteString3,HourMinuteString4,HourMinuteString5);
            System.out.println(hourminutes.toString());
            timeTable.getItems().add(hourminutes);
        }
//        timeTable.lookup(".column-header-background").setVisible(false);
//        timeTable.lookup(".show-hide-columns-button").setVisible(false);
        timeTable.getSelectionModel().setCellSelectionEnabled(true);

        ObservableList<TablePosition> selectedCells = timeTable.getSelectionModel().getSelectedCells();
        selectedCells.addListener((ListChangeListener.Change<? extends TablePosition> change) -> {
            if (selectedCells.size() > 0) {
                TablePosition selectedCell = selectedCells.get(0);
                TableColumn<HourMinutes, String> column = (TableColumn<HourMinutes, String>) selectedCell.getTableColumn();
                int rowIndex = selectedCell.getRow();
                cellData = column.getCellObservableValue(rowIndex).getValue();

                // You can now use 'data' which represents the value in the selected cell

            }
        });


    }

    // Other methods

    private List<Test> getTestData(int user_id) throws EntityNotFoundException {
        List<Test> result = testDao.getAllUserTests(user_id);
        return result;
    }

    @FXML
    void saveTestTerm(ActionEvent event) throws EntityNotFoundException {
        saveTest();
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
    private List<String> stationsToString(){
        List<String> stationsToString = new ArrayList<>(stations.size());
        for(Station station: stations){
            String town = station.getTown();
            String address = station.getAddress();
            stationsToString.add(town + ", " + address);
        }
        return stationsToString;
    }
    private void saveTest() throws EntityNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(cellData, formatter);
        Date utilDate = java.sql.Date.valueOf(pickedDate);
        Time utilTime = java.sql.Time.valueOf(localTime);
        Test test = new Test(utilDate, user.getId(),utilTime,0);
        test.setShift_id(2);
        testDao.save(test);
    }
}
