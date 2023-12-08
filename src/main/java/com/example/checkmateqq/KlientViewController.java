package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.Test;
import com.example.checkmateqq.triedy.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    private DatePicker date;

    @FXML
    private Button selectTerm;

    @FXML
    private ChoiceBox<String> station;

    @FXML
    private TableView<ForTestTable> testTable;

    private User user;

    private TestDao testDao = DaoFactory.INSTANCE.getTestDao();

    static private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    private List<Station> stations = stationDao.getAll();
    @FXML
    void logOut(MouseEvent event) {
        LoginController loginController = new LoginController();
        Stage stage = (Stage) selectTerm.getScene().getWindow();
        goToLogin(stage, loginController);
    }
    @FXML
    void pickDate(ActionEvent event) {

    }
    public void setUserId(User user) {
        this.user = user;
    }
    public void initialize() throws EntityNotFoundException {
       // System.out.println(user.get);
        UserNameOnTopBar.setText(user.getName());
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
    }

    // Other methods

    private List<Test> getTestData(int user_id) throws EntityNotFoundException {
        List<Test> result = testDao.getAllUserTests(user_id);
        return result;
    }

    @FXML
    void saveTestTerm(ActionEvent event) {

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
}
