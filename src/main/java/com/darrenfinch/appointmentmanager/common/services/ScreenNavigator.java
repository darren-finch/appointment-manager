package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.di.ControllerDependencyInjector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Stack;

public class ScreenNavigator {
    private final Stage stage;

    private final HashMap<String, Object> arguments = new HashMap<>();

    private BaseController currentController;

    private final Stack<String> screenStack = new Stack<>();

    public ScreenNavigator(Stage stage, ResourceBundle bundle) {
        this.stage = stage;

        ControllerDependencyInjector.setBundle(bundle);
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
        switchToScreen(screenResourceName, false);
    }

    private void switchToScreen(String screenResourceName, boolean goingBack) {
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

        // If we reach here the screen navigation was successful.
        // Push new screen to stack unless it's the same one that's on the stack, or we are going back.
        if (screenStack.isEmpty()) {
            screenStack.push(screenResourceName);
        }
        else if (!Objects.equals(screenStack.peek(), screenResourceName) && !goingBack) {
            screenStack.push(screenResourceName);
        }
    }

    public Object getArgument(String key) {
        return arguments.get(key);
    }

    public void goBack() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
            switchToScreen(screenStack.peek(), true);
        }
    }
}