module com.example.sunlabswipe {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.sql;

    opens com.example.sunlabswipe to javafx.fxml;
    exports com.example.sunlabswipe;
}