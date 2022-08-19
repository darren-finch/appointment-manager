package com.darrenfinch.appointmentmanager.di;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import com.darrenfinch.appointmentmanager.ScreenNavigator;

public class BaseControllerConfig {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final MainRepository mainRepository;

    public BaseControllerConfig(ScreenNavigator screenNavigator, DialogManager dialogManager) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;

        this.mainRepository = new MainRepositoryImpl();
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
}
