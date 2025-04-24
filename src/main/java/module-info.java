module org.example.medical {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;
    requires java.json;


    opens org.example.medical to javafx.fxml;
    exports org.example.medical;
}