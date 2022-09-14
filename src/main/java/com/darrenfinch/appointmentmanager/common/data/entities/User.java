package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A User is part of the technical staff that can log in to the appointment scheduling software and manage other
 * database entities such as Customers or Appointments.
 */
public class User {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    /**
     * Constructs a User with some starting values for its properties.
     * The id can be anything, since the database auto-generates it. It is just used to keep track of the entity in the application.
     */
    public User(int id, String name, String password) {
        this.id.set(id);
        this.name.set(name);
        this.password.set(password);
    }

    /**
     * Gets the id value from the id property.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Gets the id property.
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Gets the name value from the name property.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the name property.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the password value from the password property.
     */
    public String getPassword() {
        return password.get();
    }

    /**
     * Gets the password property.
     */
    public StringProperty passwordProperty() {
        return password;
    }
}