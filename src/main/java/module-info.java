module com.darrenfinch.appointmentmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.darrenfinch.appointmentmanager to javafx.fxml;
    exports com.darrenfinch.appointmentmanager;
    exports com.darrenfinch.appointmentmanager.services;
    opens com.darrenfinch.appointmentmanager.services to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.screens.dashboard;
    opens com.darrenfinch.appointmentmanager.screens.dashboard to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.screens.editappointment;
    opens com.darrenfinch.appointmentmanager.screens.editappointment to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.screens.editcustomer;
    opens com.darrenfinch.appointmentmanager.screens.editcustomer to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.screens.login;
    opens com.darrenfinch.appointmentmanager.screens.login to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.screens.reports;
    opens com.darrenfinch.appointmentmanager.screens.reports to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.utils;
    opens com.darrenfinch.appointmentmanager.utils to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.data.services;
    opens com.darrenfinch.appointmentmanager.data.services to javafx.fxml;
}