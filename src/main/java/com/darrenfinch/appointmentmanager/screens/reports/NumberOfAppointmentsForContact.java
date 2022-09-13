package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class NumberOfAppointmentsForContact {
    private final StringProperty contactName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty numberOfAppointments = new SimpleIntegerProperty();

    public NumberOfAppointmentsForContact(String contactName, String email, int numberOfAppointments) {
        this.contactName.set(contactName);
        this.email.set(email);
        this.numberOfAppointments.set(numberOfAppointments);
    }

    public String getContactName() {
        return contactName.get();
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments.get();
    }

    public IntegerProperty numberOfAppointmentsProperty() {
        return numberOfAppointments;
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments.set(numberOfAppointments);
    }
}
