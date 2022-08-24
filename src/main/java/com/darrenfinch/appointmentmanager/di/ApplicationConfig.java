package com.darrenfinch.appointmentmanager.di;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import com.darrenfinch.appointmentmanager.services.UserManager;
import javafx.stage.Stage;

public class ApplicationConfig {
    ScreenNavigator screenNavigator;
    DialogManager dialogManager;
    MainRepository mainRepository;
    UserManager userManager;

    public ApplicationConfig(Stage stage) {
        this.screenNavigator = new ScreenNavigator(stage);
        this.dialogManager = new DialogManager();
        this.mainRepository = new MainRepositoryImpl();
        this.userManager = new UserManager(mainRepository);
    }

    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
