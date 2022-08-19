package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.controllers.BaseController;
import com.darrenfinch.appointmentmanager.di.BaseControllerConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Allows controllers to easily navigate between screens without messing with the guts of FXMLLoader
 */
public class ScreenNavigator {
    /**
     * The current stage
     */
    private final Stage stage;

    /**
     * The base DI configuration passed to all controllers
     */
    private BaseControllerConfig controllerConfig;

    /**
     * Constructs a new ScreenNavigator
     *
     * @param stage the starting stage
     */
    public ScreenNavigator(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the DI configuration for all controllers
     *
     * @param controllerConfig the configuration to set
     */
    public void setControllerConfig(BaseControllerConfig controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public void switchToLoginScreen() {
        switchToScreen("login.fxml");
    }

    private BaseController switchToScreen(String screenResourceName) {
        BaseController newController;
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(screenResourceName)));
            Parent root = loader.load();

            newController = loader.getController();
            newController.setupConfig(controllerConfig);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newController;
    }
}