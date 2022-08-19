package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.di.BaseControllerConfig;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        ScreenNavigator screenNavigator = new ScreenNavigator(stage);
        DialogManager dialogManager = new DialogManager();

        BaseControllerConfig controllerConfig = new BaseControllerConfig(screenNavigator, dialogManager);
        screenNavigator.setControllerConfig(controllerConfig);

        screenNavigator.switchToLoginScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}