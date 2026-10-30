module fu.se20b01.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens fu.se20b01.demo to javafx.fxml;

    exports com.fu.ch08.common;
    exports fu.se20b01.b1;
    opens com.fu.ch08.b4 to javafx.fxml;
}