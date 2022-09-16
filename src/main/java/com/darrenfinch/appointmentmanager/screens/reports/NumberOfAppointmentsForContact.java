package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumberOfAppointmentsForContact {
    private final StringProperty contactName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty numberOfAppointments = new SimpleIntegerProperty();

    /**
     * Constructs a NumberOfAppointmentsForContact with some starting values for its properties.
     */
    public NumberOfAppointmentsForContact(String contactName, String email, int numberOfAppointments) {
        this.contactName.set(contactName);
        this.email.set(email);
        this.numberOfAppointments.set(numberOfAppointments);
    }

    /**
     * Gets the contact name value from the contact name property.
     */
    public String getContactName() {
        return contactName.get();
    }

    /**
     * Gets the contact name property.
     */
    public StringProperty contactNameProperty() {
        return contactName;
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

    /**
     * Gets the number of appointments value from the number of appointments property.
     */
    public int getNumberOfAppointments() {
        return numberOfAppointments.get();
    }

    /**
     * Gets the number of appointments property.
     */
    public IntegerProperty numberOfAppointmentsProperty() {
        return numberOfAppointments;
    }
}
