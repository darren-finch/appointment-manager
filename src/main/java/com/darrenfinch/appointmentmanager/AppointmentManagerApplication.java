package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.common.di.ApplicationConfig;
import javafx.stage.Stage;

public class AppointmentManagerApplication extends javafx.application.Application {

    private ApplicationConfig config;

    /**
     * Starts the application and initializes the application configuration, which contains the dependency graph.
     */
    @Override
    public void start(Stage stage) {
        stage.setResizable(false);

        config = new ApplicationConfig(stage);

        config.getScreenNavigator().switchToLoginScreen();
    }

    /**
     * The main entry point for the application.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Cleans up any resources that the application configuration needs to have cleaned up before quitting.
     */
    @Override
    public void stop() {
        config.cleanup();
    }
}