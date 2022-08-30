package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;

public class EditAppointmentController {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final EditAppointmentModel model;

    public EditAppointmentController(ScreenNavigator screenNavigator, DialogManager dialogManager, EditAppointmentModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.model = model;
    }
}
