module com.example.checkmateqq {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.checkmateqq to javafx.fxml;
    exports com.example.checkmateqq;
}