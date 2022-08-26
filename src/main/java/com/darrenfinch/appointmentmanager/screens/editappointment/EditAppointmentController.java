package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;

public class EditAppointmentController {
    private final ScreenNavigator screenNavigator;
    private final EditAppointmentModel model;

    public EditAppointmentController(ScreenNavigator screenNavigator, EditAppointmentModel model) {
        this.screenNavigator = screenNavigator;
        this.model = model;
    }
}
