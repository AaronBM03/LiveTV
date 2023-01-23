module com.example.livetv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.livetv to javafx.fxml;
    exports com.example.livetv;
}