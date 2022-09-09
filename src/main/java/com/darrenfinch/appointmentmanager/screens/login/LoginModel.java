package com.darrenfinch.appointmentmanager.screens.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty error = new SimpleStringProperty();

    public LoginModel() {
        location.set("");
        userName.set("");
        password.set("");
        error.set("");
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getError() {
        return error.get();
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setError(String error) {
        this.error.set(error);
    }
}
