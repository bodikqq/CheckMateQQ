package com.example.checkmateqq;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

class SearchForTestViewControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/fxml/SearchForTestView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSearchForTestById(FxRobot robot) {
        // Assume that the TestIdField is the ID field in your FXML
        robot.clickOn("#TestIdField").write("123").press(KeyCode.ENTER);

        // Now, you can verify the expected behavior using TestFX assertions
        verifyThat("#text1", isVisible());
        verifyThat("#text2", isVisible());
        verifyThat("#text3", isVisible());
        verifyThat("#text4", isVisible());
        verifyThat("#name", isVisible());
        verifyThat("#surname", isVisible());
        verifyThat("#id", isVisible());
        verifyThat("#type", isVisible());
        verifyThat("#testResult", isVisible());
        verifyThat("#submitButton", isVisible());
        verifyThat("#texts", isVisible());
    }

    // Add more tests as needed
}