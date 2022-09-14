package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A Contact is a person in the organization who speaks with customers on a regular basis.
 */
public class Contact {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    /**
     * Constructs a Contact with some starting values for its properties.
     * The id can be anything, since the database pre-populates the countries. It is just used to keep track of the entity in the application.
     */
    public Contact(int id, String name, String email) {
        this.id.set(id);
        this.name.set(name);
        this.email.set(email);
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
     * Gets the email value from the email property.
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Gets the email property.
     */
    public StringProperty emailProperty() {
        return email;
    }
}
