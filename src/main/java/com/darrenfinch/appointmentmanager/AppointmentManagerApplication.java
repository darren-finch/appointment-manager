package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.common.di.ApplicationConfig;
import javafx.stage.Stage;

public class AppointmentManagerApplication extends javafx.application.Application {

    private ApplicationConfig config;

    @Override
    public void start(Stage stage) {
        config = new ApplicationConfig(stage);

        config.getScreenNavigator().switchToLoginScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        config.cleanup();
    }
}