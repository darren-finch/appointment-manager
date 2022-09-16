package com.darrenfinch.appointmentmanager.screens.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty error = new SimpleStringProperty();

    /**
     * Constructs the model with default values.
     */
    public LoginModel() {
        location.set("");
        userName.set("");
        password.set("");
        error.set("");
    }

    /**
     * Gets the location value from the location property.
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * Gets the location property
     */
    public StringProperty locationProperty() {
        return location;
    }

    /**
     * Gets the user name value from the user name property.
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Gets the user name property
     */
    public StringProperty userNameProperty() {
        return userName;
    }

    /**
     * Gets the password value from the password property.
     */
    public String getPassword() {
        return password.get();
    }

    /**
     * Gets the password property
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Gets the error value from the error property.
     */
    public String getError() {
        return error.get();
    }

    /**
     * Gets the error property
     */
    public StringProperty errorProperty() {
        return error;
    }

    /**
     * Sets the location property.
     */
    public void setLocation(String location) {
        this.location.set(location);
    }

    /**
     * Sets the user name property.
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    /**
     * Sets the password property.
     */
    public void setPassword(String password) {
        this.password.set(password);
    }

    /**
     * Sets the error property.
     */
    public void setError(String error) {
        this.error.set(error);
    }
}
