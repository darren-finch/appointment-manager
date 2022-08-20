package com.darrenfinch.appointmentmanager.services;

import com.darrenfinch.appointmentmanager.di.ControllerDependencyInjector;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenNavigator {
    private final Stage stage;

    public ScreenNavigator(Stage stage) {
        this.stage = stage;
    }

    public void switchToLoginScreen() {
        switchToScreen("login.fxml");
    }

    public void switchToDashboardScreen() {
        switchToScreen("dashboard.fxml");
    }

    public void switchToEditCustomerScreen() {
        switchToScreen("editcustomer.fxml");
    }

    public void switchToEditAppointmentScreen() {
        switchToScreen("editappointment.fxml");
    }

    public void switchToReportsScreen() {
        switchToScreen("reports.fxml");
    }

    private void switchToScreen(String screenResourceName) {
        try {
            Parent root = ControllerDependencyInjector.load(screenResourceName);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}