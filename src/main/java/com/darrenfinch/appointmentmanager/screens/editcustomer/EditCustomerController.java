package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.services.ScreenNavigator;

public class EditCustomerController {
    private final ScreenNavigator screenNavigator;
    private final EditCustomerModel model;

    public EditCustomerController(ScreenNavigator screenNavigator, EditCustomerModel model) {
        this.screenNavigator = screenNavigator;
        this.model = model;
    }
}
