package com.darrenfinch.appointmentmanager.screens.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {
    private final StringProperty locationProperty = new SimpleStringProperty();
    private final StringProperty userNameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty errorProperty = new SimpleStringProperty();

    public LoginModel() {
        locationProperty.set("");
        userNameProperty.set("");
        passwordProperty.set("");
        errorProperty.set("");
    }

    public StringProperty locationProperty() {
        return locationProperty;
    }

    public StringProperty userNameProperty() {
        return userNameProperty;
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    public StringProperty errorProperty() {
        return errorProperty;
    }
}
