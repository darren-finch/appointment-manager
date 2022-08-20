package com.darrenfinch.appointmentmanager.di;

import com.darrenfinch.appointmentmanager.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import javafx.stage.Stage;

public class ApplicationConfig {
    ScreenNavigator screenNavigator;
    DialogManager dialogManager;

    public ApplicationConfig(Stage stage) {
        this.screenNavigator = new ScreenNavigator(stage);
        this.dialogManager = new DialogManager();
    }

    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }
}
