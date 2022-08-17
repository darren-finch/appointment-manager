module com.darrenfinch.appointmentmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.darrenfinch.appointmentmanager to javafx.fxml;
    exports com.darrenfinch.appointmentmanager;
}