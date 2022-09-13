module com.darrenfinch.appointmentmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;

    exports com.darrenfinch.appointmentmanager;
    exports com.darrenfinch.appointmentmanager.common.services;
    opens com.darrenfinch.appointmentmanager.common.services to javafx.fxml;
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
    exports com.darrenfinch.appointmentmanager.common.utils;
    opens com.darrenfinch.appointmentmanager.common.utils to javafx.fxml;

    opens com.darrenfinch.appointmentmanager.common.data.entities to javafx.base;
    exports com.darrenfinch.appointmentmanager.common.data;
    opens com.darrenfinch.appointmentmanager.common.data to javafx.fxml;
    exports com.darrenfinch.appointmentmanager.common.data.misc;
    opens com.darrenfinch.appointmentmanager.common.data.misc to javafx.fxml;
}