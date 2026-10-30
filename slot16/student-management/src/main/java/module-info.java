module com.example.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentmanagement to javafx.fxml;
    exports com.example.studentmanagement;
}