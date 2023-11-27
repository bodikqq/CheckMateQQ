module com.example.checkmateqq {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires spring.jdbc;
    requires java.naming;
    requires mysql.connector.j;
    requires java.sql;

    opens com.example.checkmateqq to javafx.fxml;
    exports com.example.checkmateqq;
    exports com.example.checkmateqq.triedy;
    opens com.example.checkmateqq.triedy to javafx.fxml;
}