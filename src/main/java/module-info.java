module fu.se20b01.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens fu.se20b01.demo to javafx.fxml;

    exports fu.se20b01.b1;
}