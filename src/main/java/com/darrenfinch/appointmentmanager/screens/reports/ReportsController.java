package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;

public class ReportsController {
    private final ScreenNavigator screenNavigator;
    private final ReportsModel model;

    public ReportsController(ScreenNavigator screenNavigator, ReportsModel model) {
        this.screenNavigator = screenNavigator;
        this.model = model;
    }
}