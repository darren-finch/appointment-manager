package com.darrenfinch.appointmentmanager.screens.login;

import com.darrenfinch.appointmentmanager.services.ScreenNavigator;

public class LoginController {
    private final ScreenNavigator screenNavigator;
    private final LoginModel model;

    public LoginController(ScreenNavigator screenNavigator, LoginModel model) {
        this.screenNavigator = screenNavigator;
        this.model = model;
    }
}