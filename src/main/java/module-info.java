module com.darrenfinch.appointmentmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.darrenfinch.appointmentmanager to javafx.fxml;
    exports com.darrenfinch.appointmentmanager;
    exports com.darrenfinch.appointmentmanager.controllers;
    opens com.darrenfinch.appointmentmanager.controllers to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.services;
    opens com.darrenfinch.appointmentmanager.services to javafx.fxml;
}