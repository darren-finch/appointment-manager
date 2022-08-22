package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.services.DialogManager;
import com.darrenfinch.appointmentmanager.services.ScreenNavigator;

public class DashboardController {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final DashboardModel model;

    public DashboardController(ScreenNavigator screenNavigator, DialogManager dialogManager, DashboardModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.model = model;
    }
}