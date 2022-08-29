package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.di.ControllerDependencyInjector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenNavigator {
    private final Stage stage;

    private final HashMap<String, Object> arguments = new HashMap<>();

    private BaseController currentController;

    public ScreenNavigator(Stage stage) {
        this.stage = stage;
    }

    public void switchToLoginScreen() {
        switchToScreen("login.fxml");
    }

    public void switchToDashboardScreen() {
        switchToScreen("dashboard.fxml");
    }

    public void switchToEditCustomerScreen(int customerId) {
        arguments.clear();
        arguments.put("customerId", customerId);
        switchToScreen("edit-customer.fxml");
    }

    public void switchToEditAppointmentScreen(int appointmentId) {
        arguments.clear();
        arguments.put("appointmentId", appointmentId);
        switchToScreen("edit-appointment.fxml");
    }

    public void switchToReportsScreen() {
        switchToScreen("reports.fxml");
    }

    private void switchToScreen(String screenResourceName) {
        if (currentController != null) {
            currentController.onStopRequest();
        }

        try {
            FXMLLoader injectedLoader = ControllerDependencyInjector.getLoader("/" + screenResourceName);
            currentController = injectedLoader.getController();
            Parent root = injectedLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (ClassCastException ignored) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getArgument(String key) {
        return arguments.get(key);
    }
}