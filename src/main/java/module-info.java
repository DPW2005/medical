module org.example.medical {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;
    requires java.sql;
    requires mysql.connector.java;


    opens org.example.medical to javafx.fxml;
    exports org.example.medical;
}