module org.example.medical {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.medical to javafx.fxml;
    exports org.example.medical;
}