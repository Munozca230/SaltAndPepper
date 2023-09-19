module com.pos.saltandpepper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.pos.saltandpepper to javafx.fxml;
    exports com.pos.saltandpepper;
}